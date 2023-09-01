package com.example.myapplication

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.example.myapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var turnFunctionButton: Button
    private lateinit var imageOfFunctionView: ImageView;
    private lateinit var nameOFFunctionView: TextView;

    lateinit var flashLight: FlashLight;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bindAll()

        setContentView(binding.root)

        getFlashLight();

        setDefaultFunction();

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
                Log.d("setItemClickListenerByName", "setListiner")

                if (flashLight.getTurnStatus()) {
                    flashLight.turnOff()
                } else {
                    flashLight.turnOn();
                }
            }
        }
    }



    private fun bindAll() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        turnFunctionButton = binding.turnFunctionButton
        imageOfFunctionView = binding.imageOfFunctionView
        nameOFFunctionView = binding.nameOfFunctionView
    }

}