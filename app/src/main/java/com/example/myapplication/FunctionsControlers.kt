package com.example.myapplication

import android.Manifest
import android.bluetooth.BluetoothManager
import android.content.Context
import android.hardware.camera2.CameraAccessException
import android.hardware.camera2.CameraCharacteristics
import android.hardware.camera2.CameraManager


abstract class Controller {

    abstract val permissions: Array<String>


    abstract fun getTurnStatus(): Boolean
    abstract fun turnOff(): Boolean
    abstract fun turnOn(): Boolean
    abstract fun isEnabled(): Boolean
    abstract fun onUnEnabled(presenter: MainPresenterImpl)
}


class Bluetooth(context: Context) : Controller() {
    private val context: Context;

    private var isTurn = false

    private val bluetoothManager =
        context.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager

    override val permissions: Array<String> =  arrayOf(
        Manifest.permission.BLUETOOTH_SCAN,
        Manifest.permission.BLUETOOTH_CONNECT,
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.BLUETOOTH_ADMIN,
    )

    init {
        this.context = context
    }

    override fun turnOn(): Boolean =
        if (!isDiscovering()) {
            startDiscovery()
            isTurn = true
            true
        } else {
            false
        }


    override fun turnOff(): Boolean =
        if (isDiscovering()) {
            cancelDiscovery()
            isTurn = false
            true
        } else {
            false
        }

    override fun getTurnStatus(): Boolean = isDiscovering()

    override fun isEnabled(): Boolean {
        return bluetoothManager.adapter.isEnabled
    }

    override fun onUnEnabled(presenter: MainPresenterImpl) {
        presenter.onEnabledBluetooth()
    }

    private fun cancelDiscovery() {
        bluetoothManager.adapter.cancelDiscovery()
    }

    private fun isDiscovering(): Boolean {
        return bluetoothManager.adapter.isDiscovering
    }

    private fun startDiscovery() {
        bluetoothManager.adapter.startDiscovery()
    }
}

class FlashLight(context: Context) : Controller() {

    private val context: Context;

    private var isTurn = false

    private val cameraManager: CameraManager =
        context.getSystemService(Context.CAMERA_SERVICE) as CameraManager;

    private var cameraId = "";

    override val permissions: Array<String>
        get() = emptyArray()

    init {
        this.context = context;

        getCameraId()
    }

    override fun getTurnStatus(): Boolean {
        return isTurn
    }

    private fun getCameraId() {
        try {
            cameraId = cameraManager.cameraIdList[0];
        } catch (exception: CameraAccessException) {
            exception.printStackTrace()
        }
    }

    override fun isEnabled(): Boolean {
        val cameraCharacteristics = cameraManager.getCameraCharacteristics(cameraId)
        return cameraCharacteristics.get(CameraCharacteristics.FLASH_INFO_AVAILABLE) == true
    }

    override fun onUnEnabled(presenter: MainPresenterImpl) {
        presenter.onEnabledFlashLight()
    }

    override fun turnOff(): Boolean {

        if (cameraId == "") return false
        if (!isEnabled()) return false
        if (!isTurn) return true

        var isSuccess = false

        try {

            cameraManager.setTorchMode(cameraId, false)
            isTurn = false;
            isSuccess =  true

        } catch (exception: CameraAccessException) {
            exception.printStackTrace()
        }
        return isSuccess
    }

    override fun turnOn(): Boolean {

        if (cameraId == "") return false
        if (!isEnabled()) return false
        if (isTurn) return true

        var isSuccess = false

        try {
            cameraManager.setTorchMode(cameraId, true)
            isTurn = true;
            isSuccess = true

        } catch (exception: CameraAccessException) {
            exception.printStackTrace()
        }
        return isSuccess
    }


}



