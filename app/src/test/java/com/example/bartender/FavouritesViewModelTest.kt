package com.example.bartender

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.bartender.favourites.model.RandomDrink
import com.example.bartender.favourites.model.RandomModel
import com.example.bartender.favourites.viewmodel.FavouritesViewModel
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
class FavouritesViewModelTest {

    @Rule
    @JvmField
    val testSchedulerRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var repository: RepositoryContract.Favourites

    private lateinit var favouritesModel: FavouritesViewModel

    private lateinit var drinksList: List<Drink>
    private lateinit var drink: Drink
    private lateinit var randomDrink: RandomDrink
    private lateinit var randomModel : RandomModel

    @Mock
    private lateinit var getRandomObserver: androidx.lifecycle.Observer<RandomDrink>
    @Mock
    private lateinit var getFavouritesObserver: androidx.lifecycle.Observer<List<Drink>>

    @Before
    fun setup() {

        favouritesModel = FavouritesViewModel(repository)

        favouritesModel.getFavouritesData().observeForever(getFavouritesObserver)
        favouritesModel.getRandomCocktailData().observeForever(getRandomObserver)

        //val itemsList = emptyList<Item>()

        drink = Drink("007", "Dummy Drink", null, "Dummy Category", "Certainly not", "Dummy Glass", "Not for drinkin",
            "https://www.thecocktaildb.com/images/media/drink/k1xatq1504389300.jpg",  "Ing1",  "Ing2", null,  null,  null,  null)

        randomDrink = RandomDrink("007", "Dummy Drink", null, "Dummy Category", "Certainly not", "Dummy Glass", "Not for drinkin",
            "https://www.thecocktaildb.com/images/media/drink/k1xatq1504389300.jpg",  "Ing1",  "Ing2", null,  null,  null,  null)

        drinksList = listOf(dummyDrink, dummyDrink, dummyDrink)

        randomModel = RandomModel(listOf(randomDrink, randomDrink, randomDrink))

    }

    @Test
    fun `getRandomCocktail() call returns correct ListDrink type and invokes onChanged() call of the LiveData`() {

        //Given
        Mockito.`when`(repository.getRandomCocktail()).thenReturn(Single.just(randomModel))

        //When
        favouritesModel.getRandomCocktail()

        //Then
        Assert.assertTrue(favouritesModel.getRandomCocktailData().value is RandomDrink)
        Mockito.verify(getRandomObserver).onChanged(randomDrink)

    }

    @Test
    fun `getFavouritesData() database call returns correct ListDrink type and invokes onChanged() call of the LiveData`() {

        //Given
        Mockito.`when`(repository.getFavourites()).thenReturn(Single.just(drinksList))

        //When
        favouritesModel.getFavourites()

        //Then
        Assert.assertTrue(favouritesModel.getFavouritesData().value is List<Drink>)
        Mockito.verify(getFavouritesObserver).onChanged(drinksList)

    }
}