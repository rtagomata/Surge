package com.example.physics

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.*
import java.math.RoundingMode
import kotlin.random.Random

class QuestionsVel1004 : AppCompatActivity() {


    var answerButtons : Array<Button> = arrayOf()
    var answersArray = DoubleArray(4)
    var index = 1
    lateinit var questionField: TextView
    lateinit var questionNum: TextView
    lateinit var timer: CountDownTimer
    lateinit var timetext: TextView
    var timeScore: Double = 0.0
    var timeRemaining: Double = 0.0
    lateinit var correctAudioPlayer: MediaPlayer


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_questions)

        val btn1: Button = findViewById(R.id.answer1)
        val btn2: Button = findViewById(R.id.answer2)
        val btn3: Button = findViewById(R.id.answer3)
        val btn4: Button = findViewById(R.id.answer4)
        questionField = findViewById(R.id.questionField)
        questionNum = findViewById(R.id.questionNum)
        answerButtons = arrayOf(btn1, btn2, btn3, btn4)
        timetext = findViewById(R.id.timeleft)
        correctAudioPlayer = MediaPlayer.create(this, R.raw.correctanswer)

        timer = object: CountDownTimer(10000, 100)
        {
            override fun onFinish()
            {
                end()
            }

            override fun onTick(remaining :Long)
            {
                timetext.setText("${(remaining / 100) / 10.0}")
                timeRemaining = 10 - (remaining / 1000.0).toBigDecimal().setScale(1, RoundingMode.UP).toDouble()

            }
        }
        setUp()



    }
    fun end()
    {
        intent = Intent(this, QuestionsMenu::class.java)
        finish()
    }

    fun setUp()
    {
        timer.cancel()
        timer.start()

        if (index == 1 || index == 2)
        {

            setUpA()
        }
        else if (index == 3 || index == 4)
        {
            setUpV()
        }
        else if (index == 5 || index == 6)
        {
            setUpT()
        }
        else
        {
            val x = Random.nextInt(1,4)
            if (x == 1) setUpA()
            if (x == 2) setUpV()
            if (x == 3) setUpT()
        }

    }
    fun setUpA()
    {
        val (time, acceleration, velocity) = setVariables()
        answersArray = QuestionHelper.createSmallAnswersA(acceleration)
        setButtons(velocity)
        setQuestionText(velocity, acceleration, time, questionField, questionNum, 1)
    }

    fun setUpV()
    {
        val (time, acceleration, velocity) = setVariables()
        answersArray = QuestionHelper.createBigAnswersA(time, acceleration, velocity)
        setButtons(velocity)
        setQuestionText(velocity, acceleration, time, questionField, questionNum, 2)
    }

    fun setUpT()
    {
        val (time, acceleration, velocity) = setVariables()
        answersArray = QuestionHelper.createSmallAnswersA(time)
        setButtons(time)
        setQuestionText(velocity, acceleration, time, questionField, questionNum, 3)
    }



    fun setQuestionText(velocity:Double, acceleration: Double, time: Double, questionField: TextView, questionNum: TextView, type:Int)
    {

        val questionStringArray = arrayOf(
            "Velocity = $velocity",
            "Time = $time",
            "Acceleration = $acceleration"
            )


        when (type)
        {
            1 -> questionStringArray[2] = "Acceleration = x"
            2 -> questionStringArray[0] = "Velocity = x"
            3 -> questionStringArray[1] = "Time = x"
        }

        questionStringArray.shuffle()

        val questionString = questionStringArray[0] + "\n" + questionStringArray[1] + "\n" + questionStringArray[2]

        val questionNumString = "Question $index/10"
        questionField.setText(questionString)
        questionNum.setText(questionNumString)
    }


    fun setVariables(): Triple<Double, Double, Double>
    {
        val t = Random.nextInt(1,20).toBigDecimal().setScale(1, RoundingMode.UP).toDouble()
        val a = Random.nextInt(1, 20).toBigDecimal().setScale(1, RoundingMode.UP).toDouble()
        val v = (t * a).toBigDecimal().setScale(1, RoundingMode.UP).toDouble()
        return Triple(t, a, v)
    }

    fun setButtons(answer:Double)
    {
        for (i in 0..3)
        {
            answerButtons[i].setText("${answersArray[i]}")
            answerButtons[i].setOnClickListener{
                if (answer == answersArray[i] && index < 10)
                {

                    index += 1
                    timeScore += timeRemaining
                    correctAudioPlayer.start()
                    setUp()

                }
                else if (answer == answersArray[i] && index == 10)
                {
                    timer.cancel()
                    timeScore += timeRemaining
                    timeScore = timeScore.toBigDecimal().setScale(1, RoundingMode.UP).toDouble()
                    val highScores = getSharedPreferences("scores", Context.MODE_PRIVATE)
                    if (highScores.getString("Questionnaire4", "100")!!.toDouble() > timeScore)
                    {
                        highScores.edit().putString("Questionnaire4", timeScore.toString()).apply()
                        highScores.edit().putInt("mustUpdate", 3).apply()
                    }
                    intent = Intent(this, Success::class.java)
                    startActivity(intent)
                }
                else
                {
                    timer.cancel()
                    val intent = Intent(this, Failure::class.java)
                    startActivity(intent)
                }
            }
        }

    }


}

