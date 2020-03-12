package com.example.bartender.shake.view

import android.media.MediaPlayer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bartender.MyApp
import com.example.bartender.R
import com.example.bartender.di.components.DaggerShakeViewModelComponent
import com.example.bartender.di.modules.viewmodels.ShakeViewModelModule
import com.example.bartender.shake.viewmodel.ShakeViewModel
import kotlinx.android.synthetic.main.shake_fragment.*
import javax.inject.Inject

class ShakeFragment : Fragment(), OnIngredientClickListener {

    @Inject
    lateinit var shakeModel: ShakeViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.shake_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializeViewModel()

        rv_ingredients.layoutManager = LinearLayoutManager(this.context, RecyclerView.HORIZONTAL, false)

        with(shakeModel) {

            getIngredients()
            status(1)

            getIngredientData().observe(viewLifecycleOwner, Observer {

                rv_ingredients.adapter = IngredientsAdapter(it.drinks, this@ShakeFragment)
                cacheIngredients(it)
                status(2)
            })

            getIngredientNetworkErrorData().observe(viewLifecycleOwner, Observer {
                shake_tv_error.text = it
                status(3)
            })
            getDatabaseErrorData().observe(viewLifecycleOwner, Observer {
                shake_tv_error.text = it
                status(3)
            })
            getCacheSavedSuccessData().observe(viewLifecycleOwner, Observer {
                if (it) {
                    Toast.makeText(this@ShakeFragment.context, getString(R.string.txt_database_success), Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this@ShakeFragment.context,  getString(R.string.txt_database_error), Toast.LENGTH_SHORT).show()
                }
            })
        }

        //Drag target - to be completed when an item is dragged onto the shaker
        shake_shaker.let{ it.setOnDragListener(MyDragListener(it))}

        val mediaPlayer: MediaPlayer? = MediaPlayer.create(context, R.raw.shaker)

        shake_shaker.setOnClickListener {

            mediaPlayer?.start()
        }

    }

    private fun status(state: Int) {

        when (state) {

            1 -> { //Initial state - loading
                shake_status_container.visibility = View.VISIBLE
                shake_tv_error.visibility = View.GONE
                shake_pb_progress.visibility = View.VISIBLE
                shake_btn_retry.visibility = View.GONE
            }

            2 -> {  //Network Success
                shake_status_container.visibility = View.GONE
                shake_pb_progress.visibility = View.GONE
                shake_btn_retry.visibility = View.GONE
                shake_tv_error.visibility = View.GONE
                rv_ingredients.visibility = View.VISIBLE
            }

            3 -> {  //Error
                shake_status_container.visibility = View.VISIBLE
                shake_pb_progress.visibility = View.GONE
                shake_btn_retry.visibility = View.VISIBLE
                shake_tv_error.visibility = View.VISIBLE
                rv_ingredients.visibility = View.GONE

                shake_btn_retry.setOnClickListener {

                    shakeModel.getIngredients()
                    status(1)

                }
            }
        }
    }

    private fun initializeViewModel() {
        DaggerShakeViewModelComponent
            .builder()
            .appComponent((activity?.application as MyApp).component())
            .shakeViewModelModule(ShakeViewModelModule(this))
            .build()
            .injectShakeFragment(this)
    }

    override fun onIngredientClicked(view: View) {
        //Implement animation or dragging to shaker
    }
}