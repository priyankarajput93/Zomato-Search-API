package com.project.module

import com.project.ui.RestaurantViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * Created by Priyanka on 15/04/2020
 */


val viewModelModule = module {
    viewModel { RestaurantViewModel(get()) }
}