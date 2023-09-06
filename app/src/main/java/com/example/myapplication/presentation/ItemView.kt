package com.example.myapplication.presentation

import com.example.myapplication.controllers.ControllerData

interface ItemView {
    fun bindItem(controllerData: ControllerData)
}