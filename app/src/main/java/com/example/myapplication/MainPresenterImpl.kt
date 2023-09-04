package com.example.myapplication

import android.content.Context
import android.util.Log

class MainPresenterImpl(val mainView: MainView) : MainPresenter {

    lateinit var flashLight: FlashLight
    lateinit var bluetooth: Bluetooth


    lateinit var currentControllerPointer: Controller

    init {
        initFunctionsControllers(mainView as Context)
    }

    override fun initFunctionsControllers(context: Context) {
        flashLight = FlashLight(context)
        Log.d("initFunctionsControllers", "init FlashLight")
        bluetooth = Bluetooth(context)
        Log.d("initFunctionsControllers", "init Bluetooth")
        currentControllerPointer = flashLight
    }


    override fun onControllerSelected(controller: ControllerData) {
        setControllerPointerByNameId(controller.nameId)
        controller.isTurned = currentControllerPointer.getTurnStatus()
        mainView.showController(controller)
    }

    fun onViewCreated() {
        mainView.showDefaultController()
    }

    fun onBindViewItem(holder: ControllersAdapter.FunctionVeiwHolder, position: Int) {

    }

    private fun setControllerPointerByNameId(nameId: Int) {
        when (nameId) {
            R.string.flashLight -> currentControllerPointer = flashLight
            R.string.bluetooth -> currentControllerPointer = bluetooth
        }
    }



    fun onTurnButtonClicked() {

        if (!checkPermissions(currentControllerPointer.permissions)) {
            requestPermissions(currentControllerPointer.permissions)
            return
        }

        if (!currentControllerPointer.isEnabled()) {
            currentControllerPointer.onUnEnabled(this)
            return
        }


        if (currentControllerPointer.getTurnStatus()) {
            if (currentControllerPointer.turnOff()) {
                mainView.changeTurnButtonLabelToOn()
            }
        } else {
            if (currentControllerPointer.turnOn()) {
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

    fun onEnabledBluetooth() {
        mainView.askToTurnBluetooth()
    }

    fun onEnabledFlashLight() {
        mainView.askToTurnBluetooth()
    }
}