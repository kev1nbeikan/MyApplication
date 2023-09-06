package com.example.myapplication.presentation

import com.example.myapplication.ControllerData

interface MainPresenter {
    fun onControllerSelected(controllerData: ControllerData)
    fun checkPermissions(permissions: Array<String>): Boolean
    fun requestPermissions(permissions: Array<String>)
    fun onTurnButtonClicked()
    fun onEnabledBluetooth()
    fun onEnabledFlashLight()
    fun onViewCreated()
}