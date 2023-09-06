package com.example.myapplication.app

import android.app.Application
import com.example.myapplication.di.controllersModule
import com.example.myapplication.di.presenterModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@App)
            modules(listOf(controllersModule, presenterModule))
        }
    }
}