package com.example.myapplication.di

import com.example.myapplication.presentation.MainPresenterImpl
import com.example.myapplication.presentation.MainView
import org.koin.dsl.module

val presenterModule = module {
    single<MainPresenterImpl> {
        MainPresenterImpl()
    }
}