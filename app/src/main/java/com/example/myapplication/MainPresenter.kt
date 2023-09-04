package com.example.myapplication

import android.content.Context

interface MainPresenter {
    fun initFunctionsControllers(context: Context)

    fun load(function: Function)
}