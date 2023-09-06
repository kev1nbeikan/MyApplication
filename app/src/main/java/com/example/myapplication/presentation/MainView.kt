package com.example.myapplication.presentation

import com.example.myapplication.ControllerData

interface MainView {
    fun showController(controllerData: ControllerData)
    fun showDefaultController()
    fun changeTurnButtonLabelToOn()
    fun changeTurnButtonLabelToOff()
    fun onPermissionsCheckAsked(permissions: Array<String>): Boolean
    fun onPermissionRequested(permissions: Array<String>)
    fun askToTurnBluetooth()
    fun notifyFlashLightUnenable()
}