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

class QuestionsVel1001 : AppCompatActivity() {


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


/*
        val answer1 = Button(this)
        answer1.setText("25")
        val answer2 = Button(this)
        val answer3 = Button(this)
        val lo :RelativeLayout = findViewById(R.id.rootlayout)
        lo.addView(answer1)
*/
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

            setUpV()
        }
        else if (index == 3 || index == 4)
        {
            setUpD()
        }
        else if (index == 5 || index == 6)
        {
            setUpT()
        }
        else
        {
            val x = Random.nextInt(1,4)
            if (x == 1) setUpV()
            if (x == 2) setUpD()
            if (x == 3) setUpT()
        }

    }
    fun setUpV()
    {
        val (time, velocity, displacement) = setVariables()
        answersArray = QuestionHelper.createSmallAnswersA(velocity)
        setButtons(velocity)
        setQuestionText(velocity, displacement, time, questionField, questionNum, 1)
    }

    fun setUpD()
    {
        val (time, velocity, displacement) = setVariables()
        answersArray = QuestionHelper.createBigAnswersA(time, velocity, displacement)
        setButtons(displacement)
        setQuestionText(velocity, displacement, time, questionField, questionNum, 2)
    }

    fun setUpT()
    {
        val (time, velocity, displacement) = setVariables()
        answersArray = QuestionHelper.createSmallAnswersA(time)
        setButtons(time)
        setQuestionText(velocity, displacement, time, questionField, questionNum, 3)
    }



    fun setQuestionText(velocity:Double, displacement: Double, time: Double, questionField: TextView, questionNum: TextView, type:Int)
    {

        val questionStringArray = arrayOf(
            "Velocity = $velocity",
            "Displacement = $displacement",
            "Time = $time"

        )

        when (type)
        {
            1 -> questionStringArray[0] = "Velocity = x"
            2 -> questionStringArray[1] = "Displacement = x"
            3 -> questionStringArray[2] = "Time = x"
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
        val v = Random.nextInt(1, 20).toBigDecimal().setScale(1, RoundingMode.UP).toDouble()
        val d = (t * v).toBigDecimal().setScale(1, RoundingMode.UP).toDouble()
        return Triple(t, v, d)
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
                    if (highScores.getString("Questionnaire1", "100")!!.toDouble() > timeScore)
                    {
                        highScores.edit().putString("Questionnaire1", timeScore.toString()).apply()
                        highScores.edit().putInt("mustUpdate", 0).apply()
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

