package com.example.bartender.search.view

import android.content.Context
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.BackgroundColorSpan
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.view.inputmethod.InputMethodManager
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bartender.MyApp
import com.example.bartender.R
import com.example.bartender.search.database.SearchResult
import com.example.bartender.search.di.components.DaggerViewModelComponent
import com.example.bartender.search.di.modules.ViewModelModule
import com.example.bartender.search.viewmodel.SearchViewModel
import kotlinx.android.synthetic.main.search_fragment.*
import kotlinx.android.synthetic.main.search_item.view.*
import java.util.*
import javax.inject.Inject


class SearchFragment : Fragment(), RecyclerViewClickListener {

    @Inject
    lateinit var model: SearchViewModel

    var currentQuery = ""
    private var currentItemSelected: View? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.search_fragment, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rv_search.layoutManager = LinearLayoutManager(this.context)
        rv_suggestions.layoutManager = LinearLayoutManager(this.context)

        initializeSearchView()

        initializeViewModel()

        recyclerViewScrollListener()

        with(model) {

            //Initial call to database to show previous searches
            getSuggestions()

            //Observing the Suggestions Live Data
            getSuggestionsLiveData().observe(viewLifecycleOwner, Observer {

                if (it.isNotEmpty()) {
                    with(rv_suggestions) {
                        adapter = SuggestionsAdapter(highlightSearchMatch(it, search_view.query.toString()), this@SearchFragment)
                        adapter?.notifyDataSetChanged()
                        scheduleLayoutAnimation()
                        showSuggestions()
                    }
                } else {
                    showSuggestions(false)
                }
            })

            //Network call call to retrieve search query and save to DB on success
            getSearchResultsLiveData().observe(viewLifecycleOwner, Observer {

                with(rv_search) {

                    adapter = SearchAdapter(it, this@SearchFragment)
                    adapter?.notifyDataSetChanged()
                    scheduleLayoutAnimation()

                }
                model.saveCurrentSearchResults(SearchResult(search_view.query.toString().toLowerCase(Locale.ROOT).trim(), it))
                status(2)

            })

            //Observing Network Error Data

            getErrorData().observe(viewLifecycleOwner, Observer {

                tv_error.text = it
                status(3)

            })

            //Observing Database Error Data
            getDatabaseErrorData().observe(viewLifecycleOwner, Observer {

                tv_error.text = it
                status(3)

            })

            //Observing Database @Insert success
            getDatabaseDataSaved().observe(viewLifecycleOwner, Observer {

                if (it) {
                    Toast.makeText(this@SearchFragment.context, "Successfully saved search to database!", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this@SearchFragment.context, "Error saving data to database :(", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }

    private fun selectMenuItem(view: View) {

        with(view) {
            submenu_layout.visibility = View.VISIBLE
            main_menu_layout.animation = AnimationUtils.loadAnimation(context, R.anim.main_menu_animation_collapse)
            submenu_layout.animation = AnimationUtils.loadAnimation(context, R.anim.submenu_item_animation_fade_in)
        }
    }

    private fun deselectMenuItem(view: View) {

        with(view) {
            submenu_layout?.animation = AnimationUtils.loadAnimation(context, R.anim.submenu_item_animation_fade_out)
            main_menu_layout?.animation = AnimationUtils.loadAnimation(context, R.anim.main_menu_animation_expand)
            submenu_layout?.visibility = View.GONE
        }
    }

    //if scrolled, selection cancelled, and selected item is null
    private fun recyclerViewScrollListener() {
        rv_search.setOnScrollChangeListener { _, _, _, _, _ ->
            currentItemSelected?.also {
                deselectMenuItem(it)
                currentItemSelected = null
            }
        }
    }

    //
    override fun onCocktailItemClicked(view: View) {

        //If the clicked item is NOT the focused item
        if (view.holder_layout != currentItemSelected) {
            //If there is another item which is selected
            if (currentItemSelected != null) {
                //The other menu item fades out
                currentItemSelected?.also { deselectMenuItem(it) }
            }
            selectMenuItem(view) //Select item
            currentItemSelected = view.holder_layout

        } else { //If the clicked item IS the focused item
            deselectMenuItem(view)
            currentItemSelected = null
        }
    }


    override fun onSuggestionItemClicked(suggestion: String) {
        currentQuery = suggestion
        search_view.setQuery(suggestion, true)
    }

    private fun initializeViewModel() {
        DaggerViewModelComponent.builder().appComponent(
            (activity?.application as MyApp).component()
        ).viewModelModule(ViewModelModule(this)).build().injectSearchFragment(this)
    }

    //Contains the logic for searchView
    private fun initializeSearchView() {

        with(search_view) {

            isSubmitButtonEnabled = true

            setOnQueryTextListener(object : SearchView.OnQueryTextListener {

                override fun onQueryTextSubmit(query: String): Boolean {
                    model.getSearchResults(query)
                    status(1)
                    showSuggestions(false)
                    return true
                }

                override fun onQueryTextChange(newText: String): Boolean {

                    if (newText.isBlank()) {
                        //hide search results if query is empty
                        rv_search.visibility = View.GONE
                    }
                    //Get new suggestion data, unless just navigated from one
                    if (newText != currentQuery) model.getSuggestions(newText)
                    return true
                }
            })
        }
    }

    //Handles view of suggestions recycler view and its describing textView
    private fun showSuggestions(show: Boolean = true) {
        if (show) {
            rv_suggestions.visibility = View.VISIBLE
            tv_recently_searched.visibility = View.VISIBLE
        } else {
            rv_suggestions.visibility = View.GONE
            tv_recently_searched.visibility = View.GONE
        }
    }

    //Converts the returned list to list of highlighted results
    private fun highlightSearchMatch(list: List<String>, query: String): List<SpannableString> {

        val highlightedList: MutableList<SpannableString> = ArrayList()
        val foregroundColorSpan = ForegroundColorSpan(resources.getColor(R.color.black))
        val backgroundColorSpan = BackgroundColorSpan(resources.getColor(R.color.emerald_green))

        for (item in list) {
            val spannableString = SpannableString(item)
            val indexOfmatch = item.findAnyOf(listOf(query), 0, true)?.first

            if (indexOfmatch != null) {

                spannableString.setSpan(foregroundColorSpan, indexOfmatch, indexOfmatch + query.length, Spannable.SPAN_INCLUSIVE_INCLUSIVE)
                spannableString.setSpan(backgroundColorSpan, indexOfmatch, indexOfmatch + query.length, Spannable.SPAN_INCLUSIVE_INCLUSIVE)

                highlightedList.add(spannableString)
            }

        }
        return highlightedList
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
                hideSoftKeyboard(this)

            }

            3 -> {  //Error
                status_container.visibility = View.VISIBLE
                pb_progress.visibility = View.GONE
                btn_retry.visibility = View.VISIBLE
                tv_error.visibility = View.VISIBLE
                rv_search.visibility = View.GONE
                hideSoftKeyboard(this)

                btn_retry.setOnClickListener {

                    model.getSearchResults(search_view.query.toString())
                    status(1)

                }
            }
        }
    }

    private fun hideSoftKeyboard(fragment: Fragment) {

        val view = fragment.activity?.currentFocus
        if (view != null) {
            val inputManager: InputMethodManager = fragment.activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputManager.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }
}

