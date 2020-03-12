package com.example.bartender.shake.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.bartender.repository.RepositoryContract
import javax.inject.Inject

@Suppress("UNCHECKED_CAST")
class ShakeViewModelFactory @Inject constructor(private val repository: RepositoryContract.Shake) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        return ShakeViewModel(repository) as T

    }
}