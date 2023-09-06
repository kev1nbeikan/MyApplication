package com.example.myapplication.di

import com.example.myapplication.presentation.ImageControllerViewSwipeListener
import com.example.myapplication.presentation.MainPresenter
import com.example.myapplication.presentation.MainPresenterImpl
import com.example.myapplication.presentation.MainView
import org.koin.dsl.module

val presenterModule = module {
    single<MainPresenterImpl> {
        MainPresenterImpl()
    }

    single<ImageControllerViewSwipeListener> {
        ImageControllerViewSwipeListener(context = get(), presenter = get())
    }
}