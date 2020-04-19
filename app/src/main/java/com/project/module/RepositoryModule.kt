package com.project.module

import com.project.ui.RestaurantRepository
import com.project.ui.RestaurantRepositoryImpl
import org.koin.dsl.module

/**
 * Created by Priyanka on 15/04/2020
 */

val repositoryModule = module {
    // Tells Koin how to create an instance of repository
   factory<RestaurantRepository> { RestaurantRepositoryImpl(get()) }
}