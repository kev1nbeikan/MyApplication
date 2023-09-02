package com.example.myapplication

import android.Manifest
import android.bluetooth.BluetoothManager
import android.content.Context
import android.content.pm.PackageManager
import android.hardware.camera2.CameraAccessException
import android.hardware.camera2.CameraCharacteristics
import android.hardware.camera2.CameraManager
import android.net.wifi.WifiManager
import android.util.Log
import androidx.core.app.ActivityCompat


interface FunctionController {
    fun getTurnStatus(): Boolean
    fun turnOff(): Boolean
    fun turnOn(): Boolean



}


class Bluetooth(context: Context) : FunctionController {
    private val context: Context;
    private val activity: MainActivity

    private var isTurn = false

    private val bluetoothManager =
        context.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager

    private val bluetoothPermissions = arrayOf(
        Manifest.permission.BLUETOOTH_SCAN,
        Manifest.permission.BLUETOOTH_CONNECT,
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.BLUETOOTH_ADMIN,
    )

    init {
        this.context = context
        this.activity = context as MainActivity
    }

    override fun getTurnStatus(): Boolean {
        return if (checkBluetoothPermissions()) {
            bluetoothManager.adapter.isDiscovering
        } else {
            false
        }
    }


    private fun askBluetoothPermissions() {
        activity.requestPermissions(
            bluetoothPermissions, 1
        )
    }

    private fun checkBluetoothPermissions(): Boolean {
        bluetoothPermissions.forEach { permission ->
            if (ActivityCompat.checkSelfPermission(
                    context,
                    permission,
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return false
            }
        }
        return true
    }


    override fun turnOff(): Boolean {
        return if (checkBluetoothPermissions()) {
            if (bluetoothManager.adapter.isDiscovering) {
                Log.d("Bluetooth", "cancelDiscovering")
                bluetoothManager.adapter.startDiscovery()
                isTurn = false
            }
            true
        } else {
            askBluetoothPermissions()
            false
        }
    }

    override fun turnOn(): Boolean {
        return if (checkBluetoothPermissions()) {
            if (!bluetoothManager.adapter.isDiscovering) {
                Log.d("Bluetooth", "startDiscovering")
                bluetoothManager.adapter.startDiscovery()
                isTurn = true
            }
            true
        } else {
            askBluetoothPermissions()
            false
        }
    }

}

class FlashLight(context: Context) : FunctionController {

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


    override fun turnOff(): Boolean {

        if (!isTurn) {
            return true
        }

        try {
            val cameraCharacteristics = cameraManager.getCameraCharacteristics(cameraId)
            if (cameraCharacteristics.get(CameraCharacteristics.FLASH_INFO_AVAILABLE) == true) {
                cameraManager.setTorchMode(cameraId, false)
                isTurn = false;
            }
            return true
        } catch (exception: CameraAccessException) {
            exception.printStackTrace()
        }
        return false
    }

    override fun turnOn(): Boolean {

        if (isTurn) {
            return true
        }

        try {
            Log.d("flashLight", "$cameraId")
            val cameraCharacteristics = cameraManager.getCameraCharacteristics(cameraId)
            if (cameraCharacteristics.get(CameraCharacteristics.FLASH_INFO_AVAILABLE) == true) {
                cameraManager.setTorchMode(cameraId, true)
                isTurn = true;
            }
            return true
        } catch (exception: CameraAccessException) {
            exception.printStackTrace()
        }
        return false
    }

}



