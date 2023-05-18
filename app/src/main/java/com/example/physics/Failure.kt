package com.example.physics

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.RelativeLayout

class Failure : AppCompatActivity() {

    lateinit var timer: CountDownTimer


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_failure)
        val ro : RelativeLayout = findViewById(R.id.failureLayout)

        /*ro.setOnClickListener{
            end()
        }*/
        timer = object: CountDownTimer(500, 1000)
        {
            override fun onFinish()
            {
                end()
            }

            override fun onTick(remaining :Long)
            {
                println("$remaining")
            }
        }
        timer.start()

    }


    fun end()
    {
        /*
        intent = Intent(this, QuestionsMenu::class.java)
        startActivity(intent)

         */
        finish()
    }
}