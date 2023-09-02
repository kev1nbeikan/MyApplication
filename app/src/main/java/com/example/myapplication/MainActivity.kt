package com.example.myapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var turnFunctionButton: Button
    private lateinit var imageOfFunctionView: ImageView;
    private lateinit var nameOFFunctionView: TextView;

    private lateinit var adapter: FunctionAdapter
//    private val functionService: FunctionService
//        get() = (applicationContext as App).functionService

    lateinit var flashLight: FlashLight;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bindAll()

        setContentView(binding.root)

        bindFunctionAdapter()

        getFlashLight();

        setDefaultFunction();

    }

    private fun bindFunctionAdapter() {
        val manager = LinearLayoutManager(this)
        adapter = FunctionAdapter()
        adapter.data = FunctionService().getFunctions()

        binding.functionsRecycleView.adapter = adapter
        binding.functionsRecycleView.layoutManager = manager
    }

    private fun getFlashLight() {
        flashLight = FlashLight(this);
    }

    private fun setDefaultFunction() {
        setFunctionToViews(
            nameId = R.string.flashLight,
            imageId = R.mipmap.flashlight
        );
    }

    private fun setFunctionToViews(nameId: Int, imageId: Int) {
        nameOFFunctionView.setText(nameId)
        imageOfFunctionView.setImageResource(imageId)
        setItemClickListenerByName(turnFunctionButton, nameId);
    }

    private fun setItemClickListenerByName(button: Button, nameId: Int) {

        when(nameId) {

            R.string.flashLight -> button.setOnClickListener {
                if (flashLight.getTurnStatus()) {
                    flashLight.turnOff()
                    changeTurnButtonLabelToOn()
                } else {
                    flashLight.turnOn();
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

}