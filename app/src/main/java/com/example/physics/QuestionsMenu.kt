package com.example.physics

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.zanvent.mathview.MathView
import kotlin.properties.Delegates


class QuestionsMenu : AppCompatActivity() {
    lateinit var buttonVel1001: Button

    lateinit var buttonVel1002: Button
    lateinit var buttonVel1003: Button
    lateinit var mathView: MathView

    private lateinit var newRecyclerView: RecyclerView
    private lateinit var newQuestionList: ArrayList<QuestionItem>
    lateinit var formulas: Array<String>
    lateinit var descriptions: Array<String>
    lateinit var classes: Array<Class<*>>
    lateinit var scores: Array<String>
    var size by Delegates.notNull<Int>()
    lateinit var highScores: SharedPreferences


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)
        size = 6



        formulas = arrayOf(
            "\\\$\\\$Δd = vt\\\$\\\$",
            "\\\$\\\$d = v_1t_1 + v_2t_2\\\$\\\$",
            "\\\$\\\$Δv = a↖{→}Δt\\\$\\\$",
            "\\\$\\\$\\a↖{→} = {v_f-v_i}/{Δt}\\\$\\\$",
            "\\\$\\\$Δx=({v+v_i}/2)t\\\$\\\$",
            "\\\$\\\$d↖{→} = {v_it}+1/2a↖{→}t\\\$\\\$"


        )

        descriptions = arrayOf(
            "Displacement",
            "Displacecement of different times and velocity",
            "Acceleration",
            "Acceleration with different velocities",
            "Displacement with different velocities",
            "Displacement with velocity, time, and acceleration"
        )
        classes = arrayOf(
            QuestionsVel1001::class.java,
            QuestionsVel1002::class.java,
            QuestionsVel1004::class.java,
            QuestionsVel1003::class.java,
            QuestionsVel1005::class.java,
            QuestionsVel1006::class.java

        )

        highScores = getSharedPreferences("scores", Context.MODE_PRIVATE)

        scores = emptyArray()
        for (i in 1..size)
        {
            scores = scores.plus("${highScores.getString("Questionnaire$i", "100")}")

        }


        newRecyclerView = findViewById(R.id.questionRecycler)
        newRecyclerView.layoutManager = LinearLayoutManager(this)
        newRecyclerView.setHasFixedSize(true)

        newQuestionList = arrayListOf<QuestionItem>()

        for (i in 0 until size)
        {
            val question = QuestionItem(formulas[i], descriptions[i], classes[i], scores[i])
            newQuestionList.add(question)
            newQuestionList[i]
        }

        newRecyclerView.adapter = Adapter(this, newQuestionList)



    }

    override fun onResume()
    {
        super.onResume()
        val updateValue = highScores.getInt("mustUpdate", -1)
        highScores.edit().putInt("mustUpdate", -1).apply()
        if (updateValue != -1)
        {
            newQuestionList[updateValue].score = highScores.getString("Questionnaire${updateValue + 1}", newQuestionList[updateValue].score).toString()
            newRecyclerView.adapter?.notifyItemChanged(updateValue)
        }
    }






}