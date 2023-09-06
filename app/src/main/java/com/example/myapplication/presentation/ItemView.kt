package com.example.myapplication.presentation

import com.example.myapplication.domain.ControllerData

interface ItemView {
    fun bindItem(controllerData: ControllerData)
}