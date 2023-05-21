package com.example.physics

import android.content.Context
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Button
import android.widget.TextView
import android.widget.LinearLayout
import java.math.RoundingMode
import kotlin.math.pow
import kotlin.math.roundToInt
import kotlin.math.roundToLong
import kotlin.random.Random

class QuestionsVel1002 : AppCompatActivity() {

    var answerButtons : Array<Button> = arrayOf()
    var answersArray = DoubleArray(4)
    var index = 1
    lateinit var questionField: TextView
    lateinit var questionNum: TextView
    lateinit var timer: CountDownTimer
    lateinit var timetext: TextView
    var timeScore: Double = 0.0
    var timeRemaining: Double = 0.0


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
        //TODO
        intent = Intent(this, QuestionsMenu::class.java)
        finish()
    }

    fun setUp()
    {
        //TODO
        timer.cancel()
        timer.start()

        if (index == 1)
        {
            setUpV1()
        }
        else if (index == 2)
        {
            setUpV2()
        }
        else if (index == 3)
        {
            setUpT1()
        }
        else if (index == 4)
        {
            setUpT2()
        }
        else if (index == 5)
        {
            setUpD()
        }
        else
        {
            val x = Random.nextInt(1,6)
            when(x)
            {
                1 -> setUpV1()
                2 -> setUpV2()
                3 -> setUpT1()
                4 -> setUpT2()
                5 -> setUpV1()
            }

        }



    }
    //TODO

    fun setUpV1()
    {
        val (velocity1, velocity2, time1, time2, displacement) = createVariables()

        answersArray = QuestionHelper.createSmallAnswers2(velocity1, velocity2, time1, time2, displacement, velocity1)
        setButtons(velocity1)
        setQuestionText(velocity1, velocity2, time1, time2, displacement, questionField, questionNum, 0)
    }

    fun setUpV2()
    {
        val (velocity1, velocity2, time1, time2, displacement) = createVariables()
        answersArray = QuestionHelper.createSmallAnswers2(velocity1, velocity2, time1, time2, displacement, velocity2)
        setButtons(velocity2)
        setQuestionText(velocity1, velocity2, time1, time2, displacement, questionField, questionNum, 1)
    }

    fun setUpT1()
    {
        val (velocity1, velocity2, time1, time2, displacement) = createVariables()
        answersArray = QuestionHelper.createSmallAnswers2(velocity1, velocity2, time1, time2, displacement, time1)
        setButtons(time1)
        setQuestionText(velocity1, velocity2, time1, time2, displacement, questionField, questionNum, 2)
    }

    fun setUpT2()
    {
        val (velocity1, velocity2, time1, time2, displacement) = createVariables()
        answersArray = QuestionHelper.createSmallAnswers2(velocity1, velocity2, time1, time2, displacement, time2)
        setButtons(time2)
        setQuestionText(velocity1, velocity2, time1, time2, displacement, questionField, questionNum, 3)
    }

    fun setUpD()
    {
        val (velocity1, velocity2, time1, time2, displacement) = createVariables()
        answersArray = QuestionHelper.createBigAnswers2(velocity1, velocity2, time1, time2, displacement, displacement)
        setButtons(displacement)
        setQuestionText(velocity1, velocity2, time1, time2, displacement, questionField, questionNum, 4)
    }


    fun setQuestionText(velocity1:Double, velocity2:Double, time1:Double, time2:Double, displacement: Double, questionField: TextView, questionNum: TextView, type:Int)
    {
        //TODO
        var questionStringArray = arrayOf<String>()

        questionStringArray = questionStringArray.plus("Velocity 1 = $velocity1")
        questionStringArray = questionStringArray.plus("Velocity 2 = $velocity2")
        questionStringArray = questionStringArray.plus("Time 1 = $time1")
        questionStringArray = questionStringArray.plus("Time 2 = $time2")
        questionStringArray = questionStringArray.plus("Displacement = $displacement")

        when (type)
        {
            0 -> questionStringArray[type] = "Velocity 1 = x"
            1 -> questionStringArray[type] = "Velocity 2 = x"
            2 -> questionStringArray[type] = "Time 1 = x"
            3 -> questionStringArray[type] = "Time 2 = x"
            4 -> questionStringArray[type] = "Displacement = x"
        }

        questionStringArray.shuffle()

        val questionString = questionStringArray[0] + "\n" +questionStringArray[1] + "\n" +questionStringArray[2] + "\n" +questionStringArray[3] + "\n" +questionStringArray[4]
        val questionNumString = "Question $index/10"
        questionField.setText(questionString)
        questionNum.setText(questionNumString)
    }




    fun Double.round(decimals: Int): Double {
        return "%.${decimals}f".format(this).toDouble()
    }



    fun createVariables(): Array<Double>
    {
        val v1 = Random.nextInt(2,15).toBigDecimal().setScale(1, RoundingMode.UP).toDouble()
        val v2 = Random.nextInt(1, 10).toBigDecimal().setScale(1, RoundingMode.UP).toDouble()
        val t1 = Random.nextInt(2,10).toBigDecimal().setScale(1, RoundingMode.UP).toDouble()
        val t2 = Random.nextInt(2, 10).toBigDecimal().setScale(1, RoundingMode.UP).toDouble()

        val d = (t1 * v1 + t2 * v2).toBigDecimal().setScale(1, RoundingMode.UP).toDouble()
        return arrayOf(v1, v2, t1, t2, d)
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