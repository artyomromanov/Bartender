package com.example.bartender.search.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
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

class SearchFragment : Fragment(), RecyclerViewClickListener {

    @Inject
    lateinit var model : SearchViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) : View? {

        return inflater.inflate(R.layout.search_fragment, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rv_search.layoutManager = LinearLayoutManager(this.context)

        initializeSearchView()
        initializeViewModel()

        //Database call to retrieve saved search data

        model.getLastSavedSearch()
        status(1)

        model.getSavedSearchData().observe(viewLifecycleOwner, Observer {

            rv_search.adapter = SearchAdapter(it, this)
            status(2)

        })

        //Network call call to retrieve new search query and save to DB on success

        model.getSearchData().observe(viewLifecycleOwner, Observer {

            rv_search.adapter = SearchAdapter(it, this)
            model.saveCurrentSearchResults(it)
            status(2)

        })

        //Observing Network Error Data

            model.getErrorData().observe(viewLifecycleOwner, Observer {

            tv_error.text = it
            status(3)

        })

        //Observing Database Error Data
        model.getDatabaseErrorData().observe(viewLifecycleOwner, Observer {

            tv_error.text = it
            status(3)

        })

        //Observing Database successful save
        model.getDatabaseDataSaved().observe(viewLifecycleOwner, Observer {

           if(it) {
               Toast.makeText(this.context, "Successfully saved data to database!", Toast.LENGTH_SHORT).show()
           }else{
               Toast.makeText(this.context, "Error saving data to database :(", Toast.LENGTH_SHORT).show()
           }
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
                rv_search.visibility = View.VISIBLE

            }

            3 -> {  //Error
                status_container.visibility = View.VISIBLE
                pb_progress.visibility = View.GONE
                btn_retry.visibility = View.VISIBLE
                tv_error.visibility = View.VISIBLE
                rv_search.visibility = View.GONE


                btn_retry.setOnClickListener {

                    model.getSearchResults(search_view.query.toString())
                    status(1)

                }

            }

        }
    }
    override fun onCocktailItemClicked(id: String) {
        println(id)
    }
    private fun initializeViewModel() {
        DaggerViewModelComponent
            .builder()
            .appComponent((activity?.application as MyApp)
                .component())
            .viewModelModule(ViewModelModule(this)).build()
            .injectSearchFragment(this)
    }
    private fun initializeSearchView() {

        search_view.isSubmitButtonEnabled = true

        search_view.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String): Boolean {
                model.getSearchResults(query)
                status(1)
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return true
            }

        })
    }
}