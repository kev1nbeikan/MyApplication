package com.example.myapplication

interface MainView {
    fun showController(function: Function)
    fun showDefaultController()
    fun changeTurnButtonLabelToOn()
    fun changeTurnButtonLabelToOff()
}