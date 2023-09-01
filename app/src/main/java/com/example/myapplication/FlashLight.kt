package com.example.myapplication

import android.content.Context
import android.hardware.camera2.CameraAccessException
import android.hardware.camera2.CameraCharacteristics
import android.hardware.camera2.CameraManager
import android.util.Log

class FlashLight(context: Context) {

    private val context: Context;

    private var isTurn = false

    private val cameraManager: CameraManager =
        context.getSystemService(Context.CAMERA_SERVICE) as CameraManager;

    private var cameraId = "";

    init {
        this.context = context;
        getCameraId()
    }

    fun getTurnStatus(): Boolean {
        return isTurn
    }

    private fun getCameraId() {
        try {
            cameraId = cameraManager.cameraIdList[0];
        } catch (exception: CameraAccessException) {
            exception.printStackTrace()
        }
    }


    fun turnOff() {

        if (!isTurn) {
            return
        }

        try {
            val cameraCharacteristics = cameraManager.getCameraCharacteristics(cameraId)
            if (cameraCharacteristics.get(CameraCharacteristics.FLASH_INFO_AVAILABLE) == true) {
                cameraManager.setTorchMode(cameraId, false)
                isTurn = false;
            }
        } catch (exception: CameraAccessException) {
            exception.printStackTrace()
        }

    }

    fun turnOn() {

        if (isTurn) {
            return
        }

        try {
            Log.d("flashLight", "$cameraId")
            val cameraCharacteristics = cameraManager.getCameraCharacteristics(cameraId)
            if (cameraCharacteristics.get(CameraCharacteristics.FLASH_INFO_AVAILABLE) == true) {
                cameraManager.setTorchMode(cameraId, true)
                isTurn = true;
            }
        } catch (exception: CameraAccessException) {
            exception.printStackTrace()
        }
    }



}


