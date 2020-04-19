package com.project.base

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.project.BuildConfig
import com.project.module.networkModule
import com.project.module.repositoryModule
import com.project.module.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import timber.log.Timber

/**
 * Created by Priyanka on 15/04/2020
 */


class BaseApp : Application() {

    companion object {
        private lateinit var INSTANCE: BaseApp
        fun getContext(): BaseApp {
            return this.INSTANCE
        }
    }

    override fun onCreate() {
        super.onCreate()
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
        INSTANCE = this
        startKoin {
            // Use Koin Android Logger
            androidLogger()
            // declare Android context
            androidContext(this@BaseApp)
            // declare modules to use
            modules(listOf(networkModule, viewModelModule, repositoryModule))
        }
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

}