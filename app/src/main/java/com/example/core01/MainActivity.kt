package com.example.core01

import android.graphics.Color
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    private lateinit var  healthLevelTextView: TextView
    private val TAG = "CORE01"
    private lateinit var  sneezeButton: Button
    private lateinit var  blowNoseButton: Button
    private lateinit var  takeMedButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Accessing view objects (TextView, Buttons) by ID
        healthLevelTextView = findViewById(R.id.health_level_textview)
        sneezeButton = findViewById(R.id.sneeze_button)
        blowNoseButton = findViewById(R.id.blow_nose_button)
        takeMedButton = findViewById(R.id.take_medication_button)

        //initial health level to 10
        healthLevelTextView.setText("10")

        //setting listener for buttons
        sneezeButton.setOnClickListener{ healthLevel(-1) }
        takeMedButton.setOnClickListener{ healthLevel(2) }
        blowNoseButton.setOnClickListener{ playSound() }

        //set text color for health level
        setTextColor( healthLevelTextView.text.toString() )
    }

    fun healthLevel(number: Int){
        //click event for sneeze and take medication button
        var newLevel :String = healthLevelCalculator(number)
        healthLevelTextView.setText(newLevel.toString())
        setTextColor(newLevel)
    }

    fun setTextColor(healthLevel: String){
        //SET COLOR for HEALTH LEVEL
        var healthLevelToInt = healthLevel.toInt()
        if (healthLevelToInt <= 7  && healthLevelToInt > 5  ){
            healthLevelTextView.setTextColor(Color.BLUE)
        }else if (healthLevelToInt <= 5 ){
            healthLevelTextView.setTextColor(Color.RED)
        }else{
            healthLevelTextView.setTextColor(Color.GREEN)
        }
    }

    fun healthLevelCalculator(number : Int) : String{
        //Calculate health level
        var newHealthLevel : Int = 0
        var currentHealthLevel : Int =  healthLevelTextView.text.toString().toInt()
        Log.i(TAG, "Calculate Current: " + currentHealthLevel.toString())

        newHealthLevel = currentHealthLevel.toInt() + number
        if (newHealthLevel < 0){
            newHealthLevel = 0
        }
        if( newHealthLevel > 10){
            newHealthLevel = 10
        }
        Log.i(TAG, "Calculate New: " + newHealthLevel.toString())
        return newHealthLevel.toString()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState?.putString("HealthLevel", healthLevelTextView.text.toString());
        Log.i(TAG, "onSaveInstanceState")
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        val healthLevel = savedInstanceState?.getString("HealthLevel")
        healthLevelTextView.setText(healthLevel)
        setTextColor(healthLevel as String)

        Log.i(TAG, "onRestoreInstanceState")
    }


    fun playSound(){
        var mediaPlayer: MediaPlayer? = MediaPlayer.create(applicationContext, R.raw.human_breathing)
        mediaPlayer?.start() // no need to call prepare(); create() does that for you
    }
}