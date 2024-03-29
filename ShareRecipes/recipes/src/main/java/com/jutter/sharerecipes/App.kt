package com.jutter.sharerecipes

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.jutter.sharerecipes.di.appModule
import com.jutter.sharerecipes.extensions.getIsDayTheme
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import timber.log.Timber

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        if (baseContext?.getIsDayTheme()!!) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }

        initKoin()
        initTimber()
    }

    private fun initKoin() {
        startKoin {
            androidLogger()
            androidContext(this@App)
            modules(appModule)
        }
    }

    private fun initTimber() {
        Timber.plant(Timber.DebugTree())
    }
}