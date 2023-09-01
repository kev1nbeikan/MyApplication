package com.example.myapplication

import android.os.Bundle
import android.util.Log
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.example.myapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var turnFunctionButton: Button
    private lateinit var imageOfFunctionView: ImageView;
    private lateinit var nameOFFunctionView: TextView;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bindAll()

        setContentView(binding.root)

        setDefaultFunction();
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
        setItemClickListenerByName()
    }

    private fun setItemClickListenerByName(turnFunctionButton: Button, nameId: Int) {
        when(nameId) {
            R.string.flashLight -> turnFunctionButton.setOnClickListener {

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