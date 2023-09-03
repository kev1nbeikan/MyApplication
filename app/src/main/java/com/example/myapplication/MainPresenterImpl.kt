package com.example.myapplication

import android.content.Context
import android.util.Log

class MainPresenterImpl(val mainView: MainView): MainPresenter {

    lateinit var flashLight: FlashLight
    lateinit var bluetooth: Bluetooth

    lateinit var functionController: FunctionController

    override fun load(function: com.example.myapplication.Function) {
        mainView.showController(function)
    }


    override fun initFunctionsControllers(context: Context) {
        flashLight = FlashLight(context)
        Log.d("initFunctionsControllers", "init FlashLight")
        bluetooth = Bluetooth(context)
        Log.d("initFunctionsControllers", "init Bluetooth")
        functionController = flashLight
    }

    fun setControllerByNameId(nameId: Int) {
        when (nameId) {
            R.string.flashLight -> functionController = flashLight
            R.string.bluetooth -> functionController = bluetooth
        }
    }

    fun changeControllerState() {
        if (functionController.getTurnStatus()) {
            if (functionController.turnOff()) {
                mainView.changeTurnButtonLabelToOn()
            }
        } else {
            if (functionController.turnOn()) {
                mainView.changeTurnButtonLabelToOff()
            }
        }
    }

}