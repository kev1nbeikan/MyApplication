package com.example.myapplication.presentation

import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.*
import com.example.myapplication.controllers.ControllerData
import com.example.myapplication.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity(), MainView {

    private val presenter: MainPresenter by lazy { MainPresenterImpl(this) }

    private lateinit var binding: ActivityMainBinding
    private lateinit var turnControllerButton: Button
    private lateinit var imageOfControllerView: ImageView
    private lateinit var nameOFControllerView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bindAll()

        setContentView(binding.root)

        presenter.onViewCreated()
    }

    override fun showController(controllerData: ControllerData) {
        nameOFControllerView.setText(controllerData.nameId)
        imageOfControllerView.setImageResource(controllerData.imageId)
        turnControllerButton.setOnClickListener {
            presenter.onTurnButtonClicked()
        }
    }


    override fun changeTurnButtonLabelToOn() {
        turnControllerButton.setText(R.string.turnOn)
    }

    override fun changeTurnButtonLabelToOff() {
        turnControllerButton.setText(R.string.turnOff)
    }

    override fun onPermissionsCheckAsked(permissions: Array<String>): Boolean {
        permissions.forEach { permission ->
            if (!isPermissionGranted(permission)) return false
        }
        return true
    }

    private fun isPermissionGranted(permission: String): Boolean {
        val permissionStatus = ActivityCompat.checkSelfPermission(this, permission)
        return permissionStatus == PackageManager.PERMISSION_GRANTED
    }

    override fun onPermissionRequested(permissions: Array<String>) {
        requestPermissions(permissions, 1)
    }

    override fun askToTurnBluetooth() {
        Toast.makeText(this, getString(R.string.askingTurnBluetooth), Toast.LENGTH_LONG).show()
    }

    override fun notifyFlashLightUnenable() {
        Toast.makeText(this, getString(R.string.flashLightIsUnenable), Toast.LENGTH_LONG).show()
    }

    private fun bindAll() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        bindViews()
        bindFunctionAdapter()
    }

    private fun bindViews() {
        turnControllerButton = binding.turnFunctionButton
        imageOfControllerView = binding.imageOfFunctionView
        nameOFControllerView = binding.nameOfFunctionView
    }

    private fun bindFunctionAdapter() {
        binding.controllersRecycleView.adapter = ControllersAdapter(presenter)
        binding.controllersRecycleView.layoutManager = getHorizontalLinearLayoutManager()
    }


    private fun getHorizontalLinearLayoutManager(): LinearLayoutManager {
        return LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
    }
}
