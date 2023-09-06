package com.example.myapplication.presentation

import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.*
import com.example.myapplication.domain.ControllerData
import com.example.myapplication.databinding.ActivityMainBinding
import org.koin.java.KoinJavaComponent.inject


class MainActivity : AppCompatActivity(), MainView {

    private val presenter: MainPresenter by inject<MainPresenterImpl>(MainPresenterImpl::class.java)

    private lateinit var binding: ActivityMainBinding
    private lateinit var turnControllerButton: Button
    private lateinit var imageOfControllerView: ImageView
    private lateinit var nameOFControllerView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.v("activity", "onCreate")
        super.onCreate(savedInstanceState)

        bindAll()

        setContentView(binding.root)

        presenter.attach(this)
    }


    override fun onStart() {
        Log.v("activity", "onStart")
        super.onStart()
        presenter.onStart()
    }


    override fun showController(controllerData: ControllerData) {
        nameOFControllerView.setText(controllerData.nameId)
        imageOfControllerView.setImageResource(controllerData.imageId)
        imageOfControllerView.setOnTouchListener(ImageControllerViewSwipeListener(this, presenter))
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

    override fun allertFlashLightUnenable() {
        Toast.makeText(this, getString(R.string.flashLightIsUnenable), Toast.LENGTH_LONG).show()
    }

    private fun bindAll() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        bindViews()
        bindFunctionAdapter()
        setContentView(binding.root)
    }

    private fun bindViews() {
        turnControllerButton = binding.turnFunctionButton
        Log.d("bindViews", "${binding.turnFunctionButton}")
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


    override fun onResume() {
        Log.v("activity", "onResume")
        super.onResume()
    }

    override fun onStop() {
        Log.v("activity", "onStop")
        super.onStop()
    }

    override fun onDestroy() {
        Log.v("activity", "onDestroy")
        super.onDestroy()
    }
}
