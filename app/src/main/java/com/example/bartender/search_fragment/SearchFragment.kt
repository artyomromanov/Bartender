package com.example.bartender.search_fragment

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
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bartender.R
import com.example.bartender.database.SearchResult
import com.example.bartender.model.Drink
import com.example.bartender.viewmodel.CocktailsViewModel
import kotlinx.android.synthetic.main.search_fragment.*
import kotlinx.android.synthetic.main.search_item.view.*
import java.util.*


class SearchFragment(val viewModel : CocktailsViewModel) : Fragment(), RecyclerViewClickListener {

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

        recyclerViewScrollListener()

        with(viewModel) {

            //Initial call to database to show previous searches
            getSuggestions()

            //Observing the Suggestions Live Data
            getSuggestionsLiveData().observe(viewLifecycleOwner, Observer {

                if (it.isNotEmpty()) {
                    with(rv_suggestions) {
                        if(search_bar == null) println("OMG")
                        adapter = SuggestionsAdapter(
                            highlightSearchMatch(
                                it, search_bar?.query.toString()
                            ), this@SearchFragment
                        )
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
                viewModel.saveCurrentSearchResult(SearchResult(search_bar.query.toString().toLowerCase(Locale.ROOT).trim(), it))
                status(2)
            })

            //Observing Network Error Data

            getErrorData().observe(viewLifecycleOwner, Observer {
                search_tv_error.text = it
                status(3)
            })

            //Observing Database Error Data
            getDatabaseErrorData().observe(viewLifecycleOwner, Observer {
                search_tv_error.text = it
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

       viewModel.getFavouritesDataSaveSuccess().observe(viewLifecycleOwner, Observer {
            if(it){
                Toast.makeText(this@SearchFragment.context, "Successfully added to favourites!", Toast.LENGTH_SHORT).show()
            }
        })

    }

    //Plays the correct animation for select or deselect item in the search list
    private fun selectMenuItem(view: View, select: Boolean, drink: Drink? = null) {
        if(select){
            with(view) {
                submenu_layout.visibility = View.VISIBLE
                main_menu_layout.animation = AnimationUtils.loadAnimation(context, R.anim.main_menu_animation_collapse)
                submenu_layout.animation = AnimationUtils.loadAnimation(context, R.anim.submenu_item_animation_fade_in)

                submenu_layout.search_btn_add.setOnClickListener {
                    if (drink != null) {
                        viewModel.addFavourite(drink)
                    }
                }
            }
        }else{
            with(view) {
                submenu_layout?.animation = AnimationUtils.loadAnimation(context, R.anim.submenu_item_animation_fade_out)
                main_menu_layout?.animation = AnimationUtils.loadAnimation(context, R.anim.main_menu_animation_expand)
                submenu_layout?.visibility = View.GONE
            }
        }
    }
    //if scrolled, selection cancelled, and selected item is null
    private fun recyclerViewScrollListener() {
        rv_search.setOnScrollChangeListener { _, _, _, _, _ ->
            currentItemSelected?.also {
                selectMenuItem(it, false)
                currentItemSelected = null
            }
        }
    }
    //Contains the logic for menu selecting and deselecting menu items
    override fun onCocktailItemClicked(view: View, drink: Drink) {

        //If the clicked item is NOT the focused item
        if (view.holder_layout != currentItemSelected) {
            //If there is another item which is selected
            if (currentItemSelected != null) {
                //The other menu item fades out
                currentItemSelected?.also { selectMenuItem(it, false) }
            }
            selectMenuItem(view, true, drink) //Select item
            currentItemSelected = view.holder_layout

        } else { //If the clicked item IS the focused item
            selectMenuItem(view, false)
            currentItemSelected = null
        }
    }

    override fun onSuggestionItemClicked(suggestion: String) {
        currentQuery = suggestion
        search_bar.setQuery(suggestion, true)
    }

    //Contains the logic for searchView
    private fun initializeSearchView() {

        with(search_bar) {

            isSubmitButtonEnabled = true

            setOnQueryTextListener(object : SearchView.OnQueryTextListener {

                override fun onQueryTextSubmit(query: String): Boolean {
                    viewModel.getSearchResults(query)
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
                    if (newText != currentQuery) viewModel.getSuggestions(newText)
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
        val foregroundColorSpan = ForegroundColorSpan(ContextCompat.getColor(context!!, R.color.black))
        val backgroundColorSpan = BackgroundColorSpan(ContextCompat.getColor(context!!, R.color.emerald_green))

        for (item in list) {
            val spannableString = SpannableString(item)
            val indexOfMatch = item.findAnyOf(listOf(query), 0, true)?.first

            if (indexOfMatch != null) {

                spannableString.setSpan(foregroundColorSpan, indexOfMatch, indexOfMatch + query.length, Spannable.SPAN_INCLUSIVE_INCLUSIVE)
                spannableString.setSpan(backgroundColorSpan, indexOfMatch, indexOfMatch + query.length, Spannable.SPAN_INCLUSIVE_INCLUSIVE)

                highlightedList.add(spannableString)
            }

        }
        return highlightedList
    }

    private fun status(state: Int) {

        when (state) {

            1 -> { //Initial state - loading

                search_status_container.visibility = View.VISIBLE
                search_tv_error.visibility = View.GONE
                search_pb_progress.visibility = View.VISIBLE
                search_btn_retry.visibility = View.GONE

            }

            2 -> {  //Network Success
                search_status_container.visibility = View.GONE
                search_pb_progress.visibility = View.GONE
                search_btn_retry.visibility = View.GONE
                search_tv_error.visibility = View.GONE
                rv_search.visibility = View.VISIBLE
                hideSoftKeyboard(this)

            }

            3 -> {  //Error
                search_status_container.visibility = View.VISIBLE
                search_pb_progress.visibility = View.GONE
                search_btn_retry.visibility = View.VISIBLE
                search_tv_error.visibility = View.VISIBLE
                rv_search.visibility = View.GONE
                hideSoftKeyboard(this)

                search_btn_retry.setOnClickListener {

                    viewModel.getSearchResults(search_bar.query.toString())
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

