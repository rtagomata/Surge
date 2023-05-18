package com.example.physics

import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Button
import android.widget.TextView
import com.zanvent.mathview.MathView
import java.math.RoundingMode
import kotlin.random.Random

class QuestionsVel1003 : AppCompatActivity() {

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

        timer = object: CountDownTimer(21000, 1000)
        {
            override fun onFinish()
            {
                end()
            }

            override fun onTick(remaining :Long)
            {
                timetext.setText("${remaining / 1000}")
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

        when (index)
        {
            1 -> setUpA()
            2 -> setUpT()
            3 -> setUpVi()
            4 -> setUpVf()
            else ->
            {
                val x = Random.nextInt(1,5)
                when (x)
                {
                    1 -> setUpA()
                    2 -> setUpT()
                    3 -> setUpVi()
                    4 -> setUpVf()
                }

            }
        }
    }

    private fun setUpVf() {
        val (acceleration, time, velocityf, velocityi) = createVariables()

        answersArray = QuestionHelper.createBigAnswersA(acceleration, time, velocityf)
        setButtons(velocityf)
        setQuestionText(velocityf, velocityi, acceleration, time, questionField, questionNum, 0)
    }

    private fun setUpVi() {
        val (acceleration, time, velocityf, velocityi) = createVariables()

        answersArray = QuestionHelper.createBigAnswersA(acceleration, time, velocityi)
        setButtons(velocityf)
        setQuestionText(velocityf, velocityi, acceleration, time, questionField, questionNum, 1)
    }

    private fun setUpT() {
        val (acceleration, time, velocityf, velocityi) = createVariables()

        answersArray = QuestionHelper.createSmallAnswersA(velocityf)
        setButtons(velocityf)
        setQuestionText(velocityf, velocityi, acceleration, time, questionField, questionNum, 3)
    }

    private fun setUpA() {
        val (acceleration, time, velocityf, velocityi) = createVariables()

        answersArray = QuestionHelper.createSmallAnswersA(velocityf)
        setButtons(velocityf)
        setQuestionText(velocityf, velocityi, acceleration, time, questionField, questionNum, 2)
    }

    fun setQuestionText(velocityFinal:Double, velocityInitial:Double, acceleration:Double, time:Double, questionField: TextView, questionNum: TextView, type:Int)
    {
        var questionStringArray =  arrayOf<String>()

        questionStringArray = questionStringArray.plus("Velocity f = $velocityFinal")
        questionStringArray = questionStringArray.plus("Velocity i = $velocityInitial")
        questionStringArray = questionStringArray.plus("Acceleration = $acceleration")
        questionStringArray = questionStringArray.plus("Time = $time")

        when (type)
        {
            0 -> questionStringArray[type] = "Velocity f = x"
            1 -> questionStringArray[type] = "Velocity i = x"
            2 -> questionStringArray[type] = "Acceleration = x"
            3 -> questionStringArray[type] = "Time = x"
        }

        questionStringArray.shuffle()

        val questionString = questionStringArray[0] + "\n" +questionStringArray[1] + "\n" +questionStringArray[2] + "\n" +questionStringArray[3]
        val questionNumString = "Question $index/10"
        questionField.setText(questionString)
        questionNum.setText(questionNumString)

    }


    fun createVariables(): Array<Double>
    {
        val a = Random.nextInt(1,20).toBigDecimal().setScale(1, RoundingMode.UP).toDouble()
        val t = Random.nextInt(1,20).toBigDecimal().setScale(1, RoundingMode.UP).toDouble()
        val vi = Random.nextInt(0, 50).toBigDecimal().setScale(1, RoundingMode.UP).toDouble()
        val vf = (a * t + vi).toBigDecimal().setScale(1, RoundingMode.UP).toDouble()

        return arrayOf(a, t, vf, vi)
    }

    private fun setButtons(answer:Double)
    {
        for (i in 0..3)
        {
            answerButtons[i].setText("${answersArray[i]}")
            answerButtons[i].setOnClickListener{
                if (answer == answersArray[i] && index < 10)
                {
                    timeScore += timeRemaining
                    index += 1
                    setUp()
                }
                else if (answer == answersArray[i] && index == 10)
                {
                    timer.cancel()
                    timeScore += timeRemaining
                    val highScores = getSharedPreferences("scores", Context.MODE_PRIVATE)
                    if (highScores.getString("Questionnaire3", "100")!!.toDouble() > timeScore)
                    {
                        highScores.edit().putString("Questionnaire3", timeScore.toString()).apply()
                        highScores.edit().putInt("mustUpdate", 2).apply()
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