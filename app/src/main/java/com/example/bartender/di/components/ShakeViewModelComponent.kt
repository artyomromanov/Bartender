package com.example.bartender.di.components

import com.example.bartender.di.modules.viewmodels.ShakeViewModelModule
import com.example.bartender.di.scopes.ActivityScope
import com.example.bartender.shake.view.ShakeFragment
import dagger.Component

@ActivityScope
@Component(modules = [ShakeViewModelModule::class], dependencies = [AppComponent::class])
interface ShakeViewModelComponent {

    fun injectShakeFragment(fragment: ShakeFragment)

}