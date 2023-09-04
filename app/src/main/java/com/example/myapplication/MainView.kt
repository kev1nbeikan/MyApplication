package com.example.myapplication

interface MainView {
    fun showController(function: ControllerData)
    fun showDefaultController()
    fun changeTurnButtonLabelToOn()
    fun changeTurnButtonLabelToOff()
    fun onPermissionsCheckAsked(permissions: Array<String>): Boolean
    fun onPermissionRequested(permissions: Array<String>)
    fun askToTurnBluetooth()
}