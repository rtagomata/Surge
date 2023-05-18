package com.example.physics

import com.example.physics.QuestionHelper.Companion.round
import kotlin.random.Random

class QuestionHelper {
    companion object
    {
        fun createSmallAnswers2(v1:Double, v2:Double, t1: Double, t2: Double, d:Double, answer:Double): DoubleArray
        {
            val Answers = DoubleArray(4)
            var Pads = arrayOf(0,0,0)
            Answers[0] = answer
            var pad = 0
            for (i in 0..2)
            {
                while ((pad in Pads) || pad == 0)
                {pad = Random.nextInt(1,10)}
                Pads[i] = pad
            }
            Answers[1] = (answer + Pads[0]).round(2)
            Answers[2] = (answer + Pads[1]).round(2)
            Answers[3] = (answer + Pads[2]).round(2)

            Answers.shuffle()
            return Answers
        }

        fun Double.round(decimals: Int): Double {
            return "%.${decimals}f".format(this).toDouble()
        }

        fun createBigAnswers2(v1:Double, v2:Double, t1: Double, t2: Double, d:Double, answer:Double): DoubleArray
        {
            val Answers = DoubleArray(4)
            var Pads = IntArray(3)
            Answers[0] = answer
            var pad = 0
            for (i in 0..2)
            {
                while ((pad in Pads) && pad == 0) pad = Random.nextInt()
                Pads[i] = pad
            }
            Answers[1] = (d + (Random.nextInt(2,5) * v1)).round(2)

            Answers[2] = (d + (Random.nextInt(-4, -1) * v2)).round(2)
            Answers[3] = (d + (Random.nextInt(5, 10) * v2)).round(2)
            Answers.shuffle()
            return Answers
        }

        fun createSmallAnswersA(answer:Double): DoubleArray
        {
            val Answers = DoubleArray(4)
            var Pads = arrayOf(0,0,0)
            Answers[0] = answer
            var pad = 0
            for (i in 0..2)
            {
                while ((pad in Pads) || pad == 0)
                {pad = Random.nextInt(1,10)}
                Pads[i] = pad
            }
            Answers[1] = (answer + Pads[0]).round(2)
            Answers[2] = (answer + Pads[1]).round(2)
            Answers[3] = (answer + Pads[2]).round(2)

            Answers.shuffle()
            return Answers
        }

        fun createBigAnswersA(value1: Double, value2: Double, answer:Double): DoubleArray
        {
            val Answers = DoubleArray(4)
            var Pads = IntArray(3)
            Answers[0] = answer
            var pad = 0
            for (i in 0..2)
            {
                while ((pad in Pads) && pad == 0) pad = Random.nextInt()
                Pads[i] = pad
            }
            Answers[1] = (answer + (Random.nextInt(2,5) * value1)).round(2)

            Answers[2] = (answer + (Random.nextInt(-4, -1) * value2)).round(2)
            Answers[3] = (answer + (Random.nextInt(5, 10) * value2)).round(2)
            Answers.shuffle()
            return Answers
        }




    }
}