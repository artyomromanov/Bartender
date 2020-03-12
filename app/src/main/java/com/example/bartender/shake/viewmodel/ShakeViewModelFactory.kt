package com.example.bartender.shake.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.bartender.Repository
import javax.inject.Inject

class ShakeViewModelFactory @Inject constructor(private val repository: Repository.Shake) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        return ShakeViewModel(repository) as T

    }
}