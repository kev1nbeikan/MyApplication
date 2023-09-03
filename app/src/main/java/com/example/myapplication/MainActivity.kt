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


class MainActivity : AppCompatActivity(), MainView {

    private lateinit var presenter: MainPresenterImpl

    private lateinit var binding: ActivityMainBinding
    private lateinit var turnFunctionButton: Button
    private lateinit var imageOfFunctionView: ImageView;
    private lateinit var nameOFFunctionView: TextView;
    private lateinit var adapter: FunctionAdapter



    lateinit var permissionLauncher: ActivityResultLauncher<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        presenter = MainPresenterImpl(this)
        presenter.initFunctionsControllers(this)

        bindAll()

        setContentView(binding.root)

        showDefaultController()

        registerPermissionListener()
    }

    private fun bindFunctionAdapter() {
        val manager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        adapter = FunctionAdapter(presenter)
        adapter.data = FunctionService().getFunctions()

        binding.functionsRecycleView.adapter = adapter
        binding.functionsRecycleView.layoutManager = manager
    }


    override fun changeTurnButtonLabelToOn() {
        turnFunctionButton.setText(R.string.turnOn)
    }

    override fun changeTurnButtonLabelToOff() {
        turnFunctionButton.setText(R.string.turnOff)
    }


    private fun bindViews() {
        turnFunctionButton = binding.turnFunctionButton
        imageOfFunctionView = binding.imageOfFunctionView
        nameOFFunctionView = binding.nameOfFunctionView
    }

    private fun bindAll() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        bindViews()
        bindFunctionAdapter()
        presenter = MainPresenterImpl(this)
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

    override fun showController(function: Function) {
        nameOFFunctionView.setText(function.nameId)
        imageOfFunctionView.setImageResource(function.imageId)
        presenter.setControllerByNameId(function.nameId)
        turnFunctionButton.setOnClickListener {
            presenter.changeControllerState()
        }
    }

    override fun showDefaultController() {
        showController(
            Function(
                nameId = R.string.flashLight,
                imageId = R.mipmap.flashlight,
            )
        );
    }

}