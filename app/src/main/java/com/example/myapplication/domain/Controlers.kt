package com.example.myapplication.domain

import android.Manifest
import android.bluetooth.BluetoothManager
import android.content.Context
import android.hardware.camera2.CameraAccessException
import android.hardware.camera2.CameraCharacteristics
import android.hardware.camera2.CameraManager
import com.example.myapplication.R
import com.example.myapplication.presentation.MainPresenterImpl


data class ControllerData(val imageId: Int, val nameId: Int, var isTurned: Boolean = false)

class ControllersService {

    private var controllers = mutableListOf(
        ControllerData(
            imageId = R.mipmap.flashlight,
            nameId = R.string.flashLight,
        ),
        ControllerData(
            imageId = R.mipmap.bluetooth,
            nameId = R.string.bluetooth,
        )
    )

    fun getControllers(): MutableList<ControllerData> {
        return controllers
    }

}

abstract class Controller {

    abstract val permissions: Array<String>

    abstract fun isTurn(): Boolean
    abstract fun turnOff(): Boolean
    abstract fun turnOn(): Boolean
    abstract fun isEnabled(): Boolean
    abstract fun onUnEnabled(presenter: MainPresenterImpl)
}


class BluetoothDiscovery(context: Context) : Controller() {
    private val context: Context

    private val bluetoothManager =
        context.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager

    override val permissions: Array<String> = arrayOf(
        Manifest.permission.BLUETOOTH_SCAN,
        Manifest.permission.BLUETOOTH_CONNECT,
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.BLUETOOTH_ADMIN,
    )

    init {
        this.context = context
    }

    override fun turnOn(): Boolean {
        if (!isTurn()) {
            start()
        }
        return true
    }

    override fun turnOff(): Boolean {
        if (isTurn()) {
            stop()
        }
        return true
    }

    override fun isTurn(): Boolean =
        bluetoothManager.adapter.isDiscovering

    override fun isEnabled(): Boolean {
        return bluetoothManager.adapter.isEnabled
    }

    override fun onUnEnabled(presenter: MainPresenterImpl) {
        presenter.onEnabledBluetooth()
    }

    private fun stop() {
        bluetoothManager.adapter.cancelDiscovery()
    }


    private fun start() {
        bluetoothManager.adapter.startDiscovery()
    }
}

class FlashLight(context: Context) : Controller() {

    private val context: Context

    private var turnStatus = false

    private val cameraManager: CameraManager =
        context.getSystemService(Context.CAMERA_SERVICE) as CameraManager

    private var cameraId = ""

    override val permissions: Array<String> = emptyArray()

    init {
        this.context = context

        getCameraId()
    }

    override fun turnOff(): Boolean {

        if (!isTurn()) return true

        var isSuccess = false

        try {
            cameraManager.setTorchMode(cameraId, false)
            turnStatus = false
            isSuccess = true

        } catch (exception: CameraAccessException) {
            exception.printStackTrace()
        }
        return isSuccess
    }

    override fun turnOn(): Boolean {

        if (isTurn()) return true

        var isSuccess = false

        try {
            cameraManager.setTorchMode(cameraId, true)
            turnStatus = true
            isSuccess = true

        } catch (exception: CameraAccessException) {
            exception.printStackTrace()
        }
        return isSuccess
    }

    override fun isTurn(): Boolean {
        return turnStatus
    }

    private fun getCameraId() {
        try {
            cameraId = cameraManager.cameraIdList[0]
        } catch (exception: CameraAccessException) {
            exception.printStackTrace()
        }
    }

    override fun isEnabled(): Boolean {
        val cameraCharacteristics = cameraManager.getCameraCharacteristics(cameraId)
        return cameraCharacteristics.get(CameraCharacteristics.FLASH_INFO_AVAILABLE) == true || cameraId == ""
    }

    override fun onUnEnabled(presenter: MainPresenterImpl) {
        presenter.onEnabledFlashLight()
    }


}



