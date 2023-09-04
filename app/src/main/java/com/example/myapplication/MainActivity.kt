package com.example.myapplication

import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity(), MainView {

    private lateinit var presenter: MainPresenterImpl

    private lateinit var binding: ActivityMainBinding
    private lateinit var turnControllerButton: Button
    private lateinit var imageOfControllerView: ImageView;
    private lateinit var nameOFControllerView: TextView;
    private lateinit var adapter: ControllersAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bindAll()

        setContentView(binding.root)

        presenter.onViewCreated()
    }

    private fun bindFunctionAdapter() {
        val manager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        adapter = ControllersAdapter(presenter)
        adapter.data = ControllersService().getControllers()

        binding.functionsRecycleView.adapter = adapter
        binding.functionsRecycleView.layoutManager = manager
    }


    override fun changeTurnButtonLabelToOn() {
        turnControllerButton.setText(R.string.turnOn)
    }

    override fun changeTurnButtonLabelToOff() {
        turnControllerButton.setText(R.string.turnOff)
    }

    override fun onPermissionsCheckAsked(permissions: Array<String>): Boolean {
        permissions.forEach { permission ->
            if (ActivityCompat.checkSelfPermission(
                    this,
                    permission,
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return false
            }
        }
        return true
    }

    override fun onPermissionRequested(permissions: Array<String>) {
        this.requestPermissions(permissions, 1)
    }

    override fun askToTurnBluetooth() {
        Toast.makeText(this, getString(R.string.askingTurnBluetooth), Toast.LENGTH_LONG).show()
    }


    private fun bindViews() {
        turnControllerButton = binding.turnFunctionButton
        imageOfControllerView = binding.imageOfFunctionView
        nameOFControllerView = binding.nameOfFunctionView
    }

    private fun bindAll() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        bindViews()
        presenter = MainPresenterImpl(this)
        bindFunctionAdapter()
    }


    override fun showController(controller: ControllerData) {
        nameOFControllerView.setText(controller.nameId)
        imageOfControllerView.setImageResource(controller.imageId)
        turnControllerButton.text = getString(
            if (controller.isTurned)
                R.string.turnOff
            else
                R.string.turnOn
        )
        turnControllerButton.setOnClickListener {
            presenter.onTurnButtonClicked()
        }
    }

    override fun showDefaultController() {
        showController(
            ControllerData(
                nameId = R.string.flashLight,
                imageId = R.mipmap.flashlight,
            )
        );
    }

}