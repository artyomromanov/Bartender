package com.example.bartender

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.bartender.repository.RepositoryContract
import com.example.bartender.search.model.Drink
import com.example.bartender.search.viewmodel.SearchViewModel
import com.example.bartender.shake.model.Ingredient
import com.example.bartender.shake.model.Ingredients
import com.example.bartender.shake.viewmodel.ShakeViewModel
import io.reactivex.Completable
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
class ShakeViewModelTest {

    @Rule
    @JvmField
    val testSchedulerRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var repository: RepositoryContract.Shake

    private lateinit var shakeModel: ShakeViewModel

    private lateinit var dummyIngredient : Ingredient
    private lateinit var ingredients: Ingredients

    @Mock
    private lateinit var getIngredientsObserver: androidx.lifecycle.Observer<Ingredients>
    @Mock
    private lateinit var cacheIngredientsObserver: androidx.lifecycle.Observer<Boolean>

    @Before
    fun setup() {

        shakeModel = ShakeViewModel(repository)

        shakeModel.getIngredientData().observeForever(getIngredientsObserver)
        shakeModel.getCacheSavedSuccessData().observeForever(cacheIngredientsObserver)

        //val itemsList = emptyList<Item>()

        dummyIngredient = Ingredient("Vodka")

        ingredients = Ingredients(listOf(dummyIngredient, dummyIngredient, dummyIngredient))

    }

    @Test
    fun `getIngredients() call returns correct ListDrink type and invokes onChanged() call of the LiveData`(){

        //Given
        Mockito.`when`(repository.getIngredientsOnline()).thenReturn(Single.just(ingredients))

        //When
        shakeModel.getIngredients()

        //Then
        Assert.assertTrue(shakeModel.getIngredientData().value is Ingredients)
        Mockito.verify(getIngredientsObserver).onChanged(ingredients)

    }

    @Test
    fun `cacheIngredients() call returns correct ListDrink type and invokes onChanged() call of the LiveData`(){

        //Given
        Mockito.`when`(repository.cacheIngredients(ingredients)).thenReturn(Completable.complete())

        //When
        shakeModel.cacheIngredients(ingredients)

        //Then
        Assert.assertTrue(shakeModel.getCacheSavedSuccessData().value!!)
        Mockito.verify(cacheIngredientsObserver).onChanged(true)

    }
}