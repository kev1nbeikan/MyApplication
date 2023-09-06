package com.example.myapplication.presentation

import android.content.Context
import com.example.myapplication.*
import com.example.myapplication.controllers.*

class MainPresenterImpl(private val mainView: MainView) : MainPresenter {

    private val flashLight: FlashLight by lazy { FlashLight(mainView as Context) }
    private val bluetoothDiscovery: BluetoothDiscovery by lazy { BluetoothDiscovery(mainView as Context) }


    private lateinit var currentController: Controller


    private val controllersData = ControllersService().getControllers()

    override val itemsCount: Int
        get() = controllersData.size

    override fun onControllerSelected(controllerData: ControllerData) {
        setCurrentController(controllerData.nameId)
        controllerData.isTurned = currentController.isTurn()
        if (controllerData.isTurned) {
            mainView.changeTurnButtonLabelToOff()
        } else {
            mainView.changeTurnButtonLabelToOn()
        }
        mainView.showController(controllerData)
    }

    override fun onViewCreated() {
        onControllerSelected(
            ControllerData(
                imageId = R.mipmap.flashlight,
                nameId = R.string.flashLight,
            )
        )
    }

    override fun onBindItemView(itemView: ItemView, position: Int) {
        itemView.bindItem(controllersData[position])
    }

    private fun setCurrentController(nameId: Int) {
        when (nameId) {
            R.string.flashLight -> currentController = flashLight
            R.string.bluetooth -> currentController = bluetoothDiscovery
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


        if (currentController.isTurn()) {
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