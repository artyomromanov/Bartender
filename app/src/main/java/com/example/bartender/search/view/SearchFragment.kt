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
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bartender.MyApp
import com.example.bartender.R
import com.example.bartender.repository.database.SearchResult
import com.example.bartender.di.components.DaggerViewModelComponent
import com.example.bartender.di.modules.viewmodels.FavouritesViewModelModule
import com.example.bartender.di.modules.viewmodels.SearchViewModelModule
import com.example.bartender.di.modules.viewmodels.ShakeViewModelModule
import com.example.bartender.favourites.viewmodel.FavouritesViewModel
import com.example.bartender.search.model.Drink
import com.example.bartender.search.viewmodel.SearchViewModel
import kotlinx.android.synthetic.main.search_fragment.*
import kotlinx.android.synthetic.main.search_item.view.*
import javax.inject.Inject
import kotlin.collections.ArrayList


class SearchFragment : Fragment(), RecyclerViewClickListener {

    @Inject
    lateinit var searchViewModel: SearchViewModel

    @Inject
    lateinit var favouritesViewModel: FavouritesViewModel

    var currentQuery = ""
    var favourites = mutableSetOf<String>()
    private var currentItemSelected: View? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.search_fragment, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rv_search.layoutManager = LinearLayoutManager(this.context)
        rv_suggestions.layoutManager = LinearLayoutManager(this.context)

        initializeSearchView()

        initializeViewModels()

        recyclerViewScrollListener()

        with(searchViewModel) {

            //Initial call to database to show previous searches
            getSuggestions()

            //Observing the Suggestions Live Data
            getSuggestionsLiveData().observe(viewLifecycleOwner, Observer {

                if (it.isNotEmpty()) {
                    with(rv_suggestions) {
                        adapter = SuggestionsAdapter(highlightSearchMatch(it, currentQuery), this@SearchFragment)
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
                searchViewModel.saveCurrentSearchResult(SearchResult(currentQuery, it))
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
                    Toast.makeText(this@SearchFragment.context, getString(R.string.txt_database_success), Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this@SearchFragment.context, getString(R.string.txt_database_error), Toast.LENGTH_SHORT).show()
                }
            })
        }

        with(favouritesViewModel) {

            //Get Favourites for interactive search list
            getFavourites()
            getFavouritesData().observe(viewLifecycleOwner, Observer {

                for (item in it) {
                    favourites.add(item.idDrink)
                }
            })

            getFavouritesDataSaveSuccess().observe(viewLifecycleOwner, Observer {
                if (it) {
                    Toast.makeText(this@SearchFragment.context, getString(R.string.txt_favourites_success), Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this@SearchFragment.context, getString(R.string.txt_favourites_error), Toast.LENGTH_SHORT).show()
                }
            })
        }
    }

    private fun initializeViewModels() {
        DaggerViewModelComponent.builder().appComponent((activity?.application as MyApp).component())
            .searchViewModelModule(SearchViewModelModule(this)).favouritesViewModelModule(
                FavouritesViewModelModule(this)
            ).shakeViewModelModule(ShakeViewModelModule(this)).build().injectSearchFragment(this)

    }

    //Select the item, and checks if it is in favourites
    private fun selectMenuItem(view: View, drink: Drink) {
        //update favourites
        favouritesViewModel.getFavourites()

        with(view) {
            submenu_layout.visibility = View.VISIBLE
            search_btn_add.visibility = View.VISIBLE
            main_menu_layout.animation = AnimationUtils.loadAnimation(context, R.anim.main_menu_animation_collapse)
            submenu_layout.animation = AnimationUtils.loadAnimation(context, R.anim.submenu_item_animation_fade_in)

            var isAdded: Boolean

            if (drink.idDrink in favourites) {
                isAdded = true
                search_btn_add.text = context.getString(R.string.search_btn_text_remove)
            } else {
                isAdded = false
                search_btn_add.text = context.getString(R.string.search_btn_text_add)
            }

            submenu_layout.search_btn_add.setOnClickListener {
                if (isAdded) {
                    favouritesViewModel.removeFavourite(drink)
                    search_btn_add.text = context.getString(R.string.search_btn_text_add)
                    isAdded = false
                } else {
                    favouritesViewModel.addFavourite(drink)
                    search_btn_add.text = context.getString(R.string.search_btn_text_remove)
                    isAdded = true
                }
            }
        }
    }

    //Deselects the item
    private fun deselectMenuItem(view: View) {
        with(view) {
            submenu_layout?.animation = AnimationUtils.loadAnimation(context, R.anim.submenu_item_animation_fade_out)
            main_menu_layout?.animation = AnimationUtils.loadAnimation(context, R.anim.main_menu_animation_expand)
            submenu_layout?.visibility = View.GONE
            search_btn_add.visibility = View.GONE
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

    //Contains the logic for menu selecting and deselecting menu items
    override fun onCocktailItemClicked(view: View, drink: Drink) {

        //If the clicked item is NOT the focused item
        if (view.holder_layout != currentItemSelected) {
            //If there is another item which is selected
            if (currentItemSelected != null) {
                //The other menu item fades out
                currentItemSelected?.also { deselectMenuItem(it) }
            }
            selectMenuItem(view, drink) //Select item
            currentItemSelected = view.holder_layout

        } else { //If the clicked item IS the focused item
            deselectMenuItem(view)
            currentItemSelected = null
        }
    }

    override fun onSuggestionItemClicked(suggestion: String) {
        currentQuery = suggestion.trim()
        search_field.setQuery(suggestion, true)
    }

    //Contains the logic for searchView
    private fun initializeSearchView() {

        with(search_field) {

            isSubmitButtonEnabled = true

            setOnQueryTextListener(object : SearchView.OnQueryTextListener {

                override fun onQueryTextSubmit(query: String): Boolean {
                    searchViewModel.getSearchResults(query.trim())
                    status(1)
                    showSuggestions(false)
                    return true
                }

                override fun onQueryTextChange(newText: String): Boolean {

                    //Get new suggestion data, unless just navigated from one
                    if (newText != currentQuery) {
                        currentQuery = newText.trim()
                        searchViewModel.getSuggestions(currentQuery)
                    }
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

                    searchViewModel.getSearchResults(search_field.query.toString())
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

