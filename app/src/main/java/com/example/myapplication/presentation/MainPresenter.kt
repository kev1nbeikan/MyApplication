package com.example.myapplication.presentation

import com.example.myapplication.controllers.ControllerData

interface MainPresenter {
    val itemsCount: Int

    fun onControllerSelected(controllerData: ControllerData)
    fun checkPermissions(permissions: Array<String>): Boolean
    fun requestPermissions(permissions: Array<String>)
    fun onTurnButtonClicked()
    fun onEnabledBluetooth()
    fun onEnabledFlashLight()
    fun onViewCreated()
    fun onBindItemView(itemView: ItemView, position: Int)
}