package com.example.physics

import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Button
import android.widget.TextView
import java.math.RoundingMode
import kotlin.random.Random

class QuestionsVel1006 : AppCompatActivity() {

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

        when (index)
        {
            1 -> setUpVi()
            2 -> setUpD()
            3 -> setUpT()
            4 -> setUpA()
            else ->
            {
                val x = Random.nextInt(1,5)
                when (x)
                {
                    1 -> setUpVi()
                    2 -> setUpD()
                    3 -> setUpT()
                    4 -> setUpA()
                }
            }
        }


    }

    fun setUpVi()
    {
        val (vi, a, t, d) = createVariables()
        answersArray = QuestionHelper.createSmallAnswersA(vi)
        setButtons(vi)
        setQuestionText(vi, a, t, d, questionField, questionNum, 0)
    }



    fun setUpD()
    {
        val (vi, a, t, d) = createVariables()
        answersArray = QuestionHelper.createBigAnswersA(vi, a, d)
        setButtons(d)
        setQuestionText(vi, a, t, d, questionField, questionNum, 3)
    }

    fun setUpT()
    {
        val (vi, a, t, d) = createVariables()
        answersArray = QuestionHelper.createSmallAnswersA(vi)
        setButtons(t)
        setQuestionText(vi, a, t, d, questionField, questionNum, 2)
    }

    fun setUpA()
    {
        val (vi, a, t, d) = createVariables()
        answersArray = QuestionHelper.createSmallAnswersA(vi)
        setButtons(a)
        setQuestionText(vi, a, t, d, questionField, questionNum, 1)
    }

    fun createVariables(): Array<Double>
    {
        val vi = Random.nextInt(2,15).toBigDecimal().setScale(1, RoundingMode.UP).toDouble()
        val a = Random.nextInt(1, 10).toBigDecimal().setScale(1, RoundingMode.UP).toDouble()
        val t = Random.nextInt(2,10).toBigDecimal().setScale(1, RoundingMode.UP).toDouble()

        val d = (vi * t + a * t * t).toBigDecimal().setScale(1, RoundingMode.UP).toDouble()
        return arrayOf(vi, a, t, d)
    }

    fun setQuestionText(vi:Double, a:Double, t:Double, d:Double, questionField: TextView, questionNum: TextView, type:Int)
    {
        var questionStringArray = arrayOf<String>()

        questionStringArray = questionStringArray.plus("Velocity i = $vi")
        questionStringArray = questionStringArray.plus("Acceleration = $a")
        questionStringArray = questionStringArray.plus("Time = $t")
        questionStringArray = questionStringArray.plus("Displacement = $d")

        when (type)

        {
            0 -> questionStringArray[type] = "Velocity i = x"
            1 -> questionStringArray[type] = "Acceleration = x"
            2 -> questionStringArray[type] = "Time= x"
            3 -> questionStringArray[type] = "Displacement = x"
        }

        questionStringArray.shuffle()

        val questionString = questionStringArray[0] + "\n" +questionStringArray[1] + "\n" +questionStringArray[2] + "\n" +questionStringArray[3]
        val questionNumString = "Question $index/10"
        questionField.setText(questionString)
        questionNum.setText(questionNumString)
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
                    if (highScores.getString("Questionnaire6", "100")!!.toDouble() > timeScore)
                    {
                        highScores.edit().putString("Questionnaire6", timeScore.toString()).apply()
                        highScores.edit().putInt("mustUpdate", 5).apply()
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