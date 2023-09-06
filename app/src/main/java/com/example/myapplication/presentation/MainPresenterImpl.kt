package com.example.myapplication.presentation

import com.example.myapplication.*
import com.example.myapplication.domain.*
import org.koin.java.KoinJavaComponent.inject

class MainPresenterImpl() : MainPresenter {

    private val flashLight: FlashLight by inject<FlashLight>(FlashLight::class.java)
    private val bluetoothDiscovery: BluetoothDiscovery by inject<BluetoothDiscovery>(BluetoothDiscovery::class.java)

    private lateinit var mainView: MainView

    private lateinit var currentController: Controller

    private val controllersData = ControllersService().getControllers()

    override val itemsCount: Int
        get() = controllersData.size


    private var currentControllerIndex: Int = 0

    override fun attach(mainView: MainView) {
        this.mainView = mainView
    }

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


    override fun onStart() {
        onControllerSelected(
            controllersData[currentControllerIndex]
        )
    }

    override fun onBindItemView(itemView: ItemView, position: Int) {
        itemView.bindItem(controllersData[position])
    }

    override fun onSwipedRight() {
        onControllerSelected(controllersData[decreaseCurrentControllerIndex()])
    }

    override fun onSwipedLeft() {
        onControllerSelected(controllersData[increaseCurrentControllerIndex()])
    }

    private fun setCurrentController(nameId: Int) {
        when (nameId) {
            R.string.flashLight -> {
                currentController = flashLight
                currentControllerIndex = 0
            }
            R.string.bluetooth -> {
                currentController = bluetoothDiscovery
                currentControllerIndex = 1
            }
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
        mainView.allertFlashLightUnenable()
    }

    private fun getCurrentControllerIndex(value: Int): Int {
        return if (currentControllerIndex == 0 && value < 0) {
            itemsCount + value
        } else {
            (currentControllerIndex + value) % 2
        }
    }

    private fun increaseCurrentControllerIndex(): Int {
        currentControllerIndex = getCurrentControllerIndex(+1)
        return currentControllerIndex
    }

    private fun decreaseCurrentControllerIndex(): Int {
        currentControllerIndex = getCurrentControllerIndex(-1)
        return currentControllerIndex
    }
}