package com.example.myapplication.presentation

import android.content.Context
import com.example.myapplication.*
import com.example.myapplication.controllers.Bluetooth
import com.example.myapplication.controllers.Controller
import com.example.myapplication.controllers.ControllerData
import com.example.myapplication.controllers.FlashLight

class MainPresenterImpl(private val mainView: MainView) : MainPresenter {

    private val flashLight: FlashLight by lazy { FlashLight(mainView as Context) }
    private val bluetooth: Bluetooth by lazy { Bluetooth(mainView as Context) }


    private lateinit var currentController: Controller

    override fun onControllerSelected(controllerData: ControllerData) {
        setCurrentController(controllerData.nameId)
        controllerData.isTurned = currentController.getTurnStatus()
        if (controllerData.isTurned) {
            mainView.changeTurnButtonLabelToOff()
        } else {
            mainView.changeTurnButtonLabelToOn()
        }
        mainView.showController(controllerData)
    }

    override fun onViewCreated() {
        mainView.showDefaultController()
    }

    fun onBindViewItem(holder: ControllersAdapter.ControllerVeiwHolder, position: Int) {

    }

    private fun setCurrentController(nameId: Int) {
        when (nameId) {
            R.string.flashLight -> currentController = flashLight
            R.string.bluetooth -> currentController = bluetooth
        }
    }


    override fun onTurnButtonClicked() {

        if (!checkPermissions(currentController.permissions)) {
            requestPermissions(currentController.permissions)
            return
        }

        if (!currentController.isEnabled()) {
            currentController.onUnEnabled(this)
            return
        }


        if (currentController.getTurnStatus()) {
            if (currentController.turnOff()) {
                mainView.changeTurnButtonLabelToOn()
            }
        } else {
            if (currentController.turnOn()) {
                mainView.changeTurnButtonLabelToOff()
            }
        }
    }

    override fun checkPermissions(permissions: Array<String>): Boolean {
        return mainView.onPermissionsCheckAsked(permissions)
    }

    override fun requestPermissions(permissions: Array<String>) {
        mainView.onPermissionRequested(permissions)
    }

    override fun onEnabledBluetooth() {
        mainView.askToTurnBluetooth()
    }

    override fun onEnabledFlashLight() {
        mainView.notifyFlashLightUnenable()
    }
}