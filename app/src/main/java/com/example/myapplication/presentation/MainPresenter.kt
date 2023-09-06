package com.example.myapplication.presentation

import com.example.myapplication.domain.ControllerData

interface MainPresenter {
    val itemsCount: Int

    fun attach(mainView: MainView)
    fun onControllerSelected(controllerData: ControllerData)
    fun checkPermissions(permissions: Array<String>): Boolean
    fun requestPermissions(permissions: Array<String>)
    fun onTurnButtonClicked()
    fun onEnabledBluetooth()
    fun onEnabledFlashLight()
    fun onStart()
    fun onBindItemView(itemView: ItemView, position: Int)
    fun onSwipedRight()
    fun onSwipedLeft()
}