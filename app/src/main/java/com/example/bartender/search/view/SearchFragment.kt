package com.example.bartender.search.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bartender.MyApp
import com.example.bartender.R
import com.example.bartender.search.di.components.DaggerViewModelComponent
import com.example.bartender.search.di.modules.ViewModelModule
import com.example.bartender.search.viewmodel.SearchViewModel
import kotlinx.android.synthetic.main.search_fragment.*
import javax.inject.Inject

class SearchFragment : Fragment() {

    @Inject
    lateinit var model : SearchViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        DaggerViewModelComponent
            .builder()
            .appComponent((activity?.application as MyApp).component())
            .viewModelModule(ViewModelModule(this))
            .build()
            .injectActivity(this)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) : View? {

        return inflater.inflate(R.layout.search_fragment, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        status(1)

        model.getSearchResults("Daiquiri")

        model.getSearchData().observe(viewLifecycleOwner, Observer {

            rv_search.layoutManager = LinearLayoutManager(this.context)

            rv_search.adapter = SearchAdapter(it)
            status(2)

        })

        model.getErrorData().observe(viewLifecycleOwner, Observer {

            tv_error.text = it
            status(3)

        })

    }
    private fun status(state: Int) {

        when (state) {

            1 -> { //Initial state - loading

                status_container.visibility = View.VISIBLE
                tv_error.visibility = View.GONE
                pb_progress.visibility = View.VISIBLE
                btn_retry.visibility = View.GONE


            }

            2 -> {  //Network Success
                status_container.visibility = View.GONE
                pb_progress.visibility = View.GONE
                btn_retry.visibility = View.GONE

                tv_error.visibility = View.GONE

            }

            3 -> {  //Error
                status_container.visibility = View.VISIBLE
                pb_progress.visibility = View.GONE
                btn_retry.visibility = View.VISIBLE

                tv_error.visibility = View.VISIBLE


                btn_retry.setOnClickListener {

                    model.getSearchResults("Daiquiri")
                    status(1)

                }

            }

        }
    }
}