package com.example.myapplication

import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var turnFunctionButton: Button
    private lateinit var imageOfFunctionView: ImageView;
    private lateinit var nameOFFunctionView: TextView;
    private lateinit var adapter: FunctionAdapter

    private lateinit var flashLight: FlashLight
    private lateinit var bluetooth: Bluetooth
    lateinit var functionController: FunctionController

    lateinit var permissionLauncher: ActivityResultLauncher<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bindAll()

        setContentView(binding.root)

        bindFunctionAdapter()

        initFunctionsControllers()

        setDefaultFunction();

        registerPermissionListener()
    }

    private fun initFunctionsControllers() {
        flashLight = FlashLight(this)
        bluetooth = Bluetooth(this)
    }

    private fun bindFunctionAdapter() {
        val manager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        adapter = FunctionAdapter(this)
        adapter.data = FunctionService().getFunctions()

        binding.functionsRecycleView.adapter = adapter
        binding.functionsRecycleView.layoutManager = manager
    }

    private fun setDefaultFunction() {
        setFunction(
            Function(
                nameId = R.string.flashLight,
                imageId = R.mipmap.flashlight,
            )
        );
    }

    fun setFunction(function: Function) {
        nameOFFunctionView.setText(function.nameId)
        imageOfFunctionView.setImageResource(function.imageId)
        setItemClickListenerByName(turnFunctionButton, function.nameId)

    }

    private fun setItemClickListenerByName(button: Button, nameId: Int) {

        when (nameId) {

            R.string.flashLight -> functionController = flashLight
            R.string.bluetooth -> functionController = bluetooth
        }

        button.setOnClickListener {
            if (functionController.getTurnStatus()) {
                if (functionController.turnOff()) {
                    changeTurnButtonLabelToOn()
                }
            } else {
                if (functionController.turnOn()) {
                    changeTurnButtonLabelToOff()
                }
            }
        }

    }

    private fun changeTurnButtonLabelToOn() {
        turnFunctionButton.setText(R.string.turnOn)
    }

    private fun changeTurnButtonLabelToOff() {
        turnFunctionButton.setText(R.string.turnOff)
    }


    private fun bindAll() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        turnFunctionButton = binding.turnFunctionButton
        imageOfFunctionView = binding.imageOfFunctionView
        nameOFFunctionView = binding.nameOfFunctionView
    }


    fun registerPermissionListener() {
        permissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) {
            if (!it) {
                Toast.makeText(this, "Permission denied", Toast.LENGTH_LONG).show()
            }
        }

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 1) {
            if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted
            } else {
                Toast.makeText(this, "Permission denied", Toast.LENGTH_LONG).show()

            }
        }
    }

}