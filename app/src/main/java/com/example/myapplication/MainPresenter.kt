package com.example.myapplication

import android.content.Context

interface MainPresenter {


    fun initFunctionsControllers(context: Context)

    fun onControllerSelected(controller: ControllerData)

    fun checkPermissions(permissions: Array<String>): Boolean
    fun requestPermissions(permissions: Array<String>)
}