package com.example.myapplication

import android.content.Context

interface MainPresenter {
    fun load(function: com.example.myapplication.Function)
    fun initFunctionsControllers(context: Context)

}