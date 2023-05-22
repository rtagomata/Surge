package com.example.physics

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.zanvent.mathview.MathView
import androidx.appcompat.app.AppCompatActivity

class Adapter (private val context: Context, private val questionList: ArrayList<QuestionItem>) :
    RecyclerView.Adapter<Adapter.MyViewHolder>() {

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        val formula: MathView = itemView.findViewById(R.id.formula)
        val description: TextView = itemView.findViewById(R.id.questionDescription)
        val score: TextView = itemView.findViewById(R.id.score)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.question_item,
        parent, false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return questionList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = questionList[position]
        holder.formula.setText(currentItem.formula)
        holder.description.setText(currentItem.description)
        holder.score.setText(currentItem.score)
        holder.formula.setTextColor("#FFFFFF")
        holder.description.setOnClickListener{
            val intent = Intent(context, currentItem.cls)
            context.startActivity(intent)
        }
        holder.formula.setOnClickListener{
            val intent = Intent(context, currentItem.cls)
            context.startActivity(intent)
        }

        if (currentItem.score != "100")
        {
            holder.score.setText(currentItem.score)
        }
        else
        {
            holder.score.setText("")
        }

    }


}