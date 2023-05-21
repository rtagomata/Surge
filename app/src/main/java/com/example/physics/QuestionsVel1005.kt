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

class QuestionsVel1005 : AppCompatActivity() {

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

            setUpX()
        }
        else if (index == 3)
        {
            setUpVi()
        }
        else if (index == 4)
        {
            setUpVf()
        }
        else if (index == 5 || index == 6)
        {
            setUpT()
        }
        else
        {
            val x = Random.nextInt(1,5)
            if (x == 1) setUpX()
            if (x == 2) setUpVi()
            if (x == 3) setUpT()
            if (x == 4) setUpVf()
        }

    }

    fun setUpX()
    {
        val (vi, vf, t, x) = setVariables()
        answersArray = QuestionHelper.createBigAnswersA(vi, vf, x)
        setButtons(x)
        setQuestionText(vi, vf, t, x, questionField, questionNum, 3)
    }

    fun setUpVi()
    {
        val (vi, vf, t, x) = setVariables()
        answersArray = QuestionHelper.createSmallAnswersA(vi)
        setButtons(vi)
        setQuestionText(vi, vf, t, x, questionField, questionNum, 0)
    }

    fun setUpVf()
    {

        val (vi, vf, t, x) = setVariables()
        answersArray = QuestionHelper.createSmallAnswersA(vf)
        setButtons(vf)
        setQuestionText(vi, vf, t, x, questionField, questionNum, 1)
    }

    fun setUpT()
    {

        val (vi, vf, t, x) = setVariables()
        answersArray = QuestionHelper.createSmallAnswersA(vi)
        setButtons(t)
        setQuestionText(vi, vf, t, x, questionField, questionNum, 2)
    }

    fun setVariables(): Array<Double>
    {
        val vi = Random.nextInt(2,15).toBigDecimal().setScale(1, RoundingMode.UP).toDouble()
        val vf = Random.nextInt(1, 10).toBigDecimal().setScale(1, RoundingMode.UP).toDouble()
        val t = Random.nextInt(2,10).toBigDecimal().setScale(1, RoundingMode.UP).toDouble()
        val x = (((vi + vf) / 2) * t).toBigDecimal().setScale(1, RoundingMode.UP).toDouble()

        return arrayOf(vi, vf, t, x)
    }

    fun setQuestionText(velocityi:Double, velocityf:Double, time:Double, displacement:Double,  questionField: TextView, questionNum: TextView, type:Int)
    {
        var questionStringArray = arrayOf<String>()

        questionStringArray = questionStringArray.plus("Velocity i = $velocityi")
        questionStringArray = questionStringArray.plus("Velocity f = $velocityf")
        questionStringArray = questionStringArray.plus("Time = $time")
        questionStringArray = questionStringArray.plus("Displacement = $displacement")

        when (type)
        {
            0 -> questionStringArray[type] = "Velocity i = x"
            1 -> questionStringArray[type] = "Velocity f = x"
            2 -> questionStringArray[type] = "Time = x"
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
                    if (highScores.getString("Questionnaire2", "100")!!.toDouble() > timeScore)
                    {
                        highScores.edit().putString("Questionnaire2", timeScore.toString()).apply()
                        highScores.edit().putInt("mustUpdate", 1).apply()
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