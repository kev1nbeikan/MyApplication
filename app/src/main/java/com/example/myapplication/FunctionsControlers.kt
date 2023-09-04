package com.example.myapplication

import android.Manifest
import android.bluetooth.BluetoothManager
import android.content.Context
import android.hardware.camera2.CameraAccessException
import android.hardware.camera2.CameraCharacteristics
import android.hardware.camera2.CameraManager
import android.util.Log


interface Controller {
    fun getTurnStatus(): Boolean
    fun turnOff(): Boolean
    fun turnOn(): Boolean


}


class Bluetooth(context: Context, val presenter: MainPresenterImpl) : Controller {
    private val context: Context;

    private var isTurn = false

    private val bluetoothManager =
        context.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager

    val bluetoothPermissions = arrayOf(
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
        if (!isEnabled()) {
            presenter.onEnabledBluetooth()
            false
        } else if (checkPermissions()) {
            if (!isDiscovering()) {
                startDiscovery()
                isTurn = true
            }
            true
        } else {
            askPermissions()
            false
        }

    override fun turnOff(): Boolean =
        if (!isEnabled()) {
            presenter.onEnabledBluetooth()
            false
        } else if (checkPermissions()) {
            if (isDiscovering()) {
                cancelDiscovery()
                isTurn = false
            }
            true
        } else {
            askPermissions()
            false
        }

    override fun getTurnStatus(): Boolean =
        if (checkPermissions()) {
            isDiscovering()
        } else {
            false
        }


    private fun askPermissions() {
        presenter.requestPermissions(bluetoothPermissions)
    }

    private fun checkPermissions(): Boolean {
        return presenter.checkPermissions(bluetoothPermissions)
    }

    private fun isEnabled(): Boolean {
        return bluetoothManager.adapter.isEnabled
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

class FlashLight(context: Context) : Controller {

    private val context: Context;

    private var isTurn = false

    private val cameraManager: CameraManager =
        context.getSystemService(Context.CAMERA_SERVICE) as CameraManager;

    private var cameraId = "";

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

    private fun isAvailable(): Boolean {
        val cameraCharacteristics = cameraManager.getCameraCharacteristics(cameraId)
        return cameraCharacteristics.get(CameraCharacteristics.FLASH_INFO_AVAILABLE) == true
    }

    override fun turnOff(): Boolean {

        if (!isAvailable()) {
            return false
        }

        if (!isTurn) {
            return true
        }

        try {
            cameraManager.setTorchMode(cameraId, false)
            isTurn = false;
            return true
        } catch (exception: CameraAccessException) {
            exception.printStackTrace()
        }
        return false
    }

    override fun turnOn(): Boolean {

        if (!isAvailable()) {
            return false
        }

        if (isTurn) {
            return true
        }

        try {
            cameraManager.setTorchMode(cameraId, true)
            isTurn = true;
            return true
        } catch (exception: CameraAccessException) {
            exception.printStackTrace()
        }
        return false
    }

}



