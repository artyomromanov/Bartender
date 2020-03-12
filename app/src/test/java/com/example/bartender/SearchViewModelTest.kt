package com.example.bartender

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.bartender.repository.RepositoryContract
import com.example.bartender.search.model.Drink
import com.example.bartender.search.viewmodel.SearchViewModel
import io.reactivex.Single
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class SearchViewModelTest {

    @Rule
    @JvmField
    val testSchedulerRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var repository: RepositoryContract.Search

    private lateinit var viewModel: SearchViewModel

    private lateinit var drinksList : List<Drink>
    private lateinit var stringList : List<String>

    @Mock
    private lateinit var searchResultsObserver: androidx.lifecycle.Observer<List<Drink>>
    @Mock
    private lateinit var suggestionResultsObserver: androidx.lifecycle.Observer<List<String>>

    @Before
    fun setup() {

        viewModel = SearchViewModel(repository)

        viewModel.getSearchResultsLiveData().observeForever(searchResultsObserver)
        viewModel.getSuggestionsLiveData().observeForever(suggestionResultsObserver)

        //val itemsList = emptyList<Item>()

        val dummyDrink = Drink("007", "Dummy Drink", null, "Dummy Category", "Certainly not", "Dummy Glass", "Not for drinkin",
            "https://www.thecocktaildb.com/images/media/drink/k1xatq1504389300.jpg",  "Ing1",  "Ing2", null,  null,  null,  null)

        drinksList = listOf(dummyDrink, dummyDrink, dummyDrink)

        stringList = listOf("One, Two, Three")

    }

    @Test
    fun `getSearchResults() call returns correct ListDrink type and invokes onChanged() call of the LiveData`(){

        val query = "Gin"

        //Given
        Mockito.`when`(repository.getSearchResults(query)).thenReturn(Single.just(drinksList))

        //When
        viewModel.getSearchResults(query)

        //Then
        Assert.assertTrue(viewModel.getSearchResultsLiveData().value is List<Drink>)
        Mockito.verify(searchResultsObserver).onChanged(drinksList)

    }

    @Test
    fun `getSuggestions() call returns correct ListDrink type and invokes onChanged() call of the LiveData`(){

        val query = "One"

        //Given
        Mockito.`when`(repository.getSuggestions(query)).thenReturn(Single.just(stringList))

        //When
        viewModel.getSuggestions(query)

        //Then
        Assert.assertTrue(viewModel.getSuggestionsLiveData().value is List<String>)
        Mockito.verify(suggestionResultsObserver).onChanged(stringList)

    }
}