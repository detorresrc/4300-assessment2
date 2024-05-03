package com.detorresrc.tech4300.assessment2.lib

import android.os.Parcelable
import android.util.Log
import kotlinx.parcelize.Parcelize

@Parcelize
data class Answers(val answers: List<Answer>) : Parcelable {
    fun getStats(): AnswersStats {
        var correct = 0f
        var incorrect = 0f

        answers.forEach { answer ->
            if (answer.isCorrect()) {
                correct += 1
            } else {
                incorrect += 1
            }
        }

        val percentage = correct.div(answers.size).times(100f)

        return AnswersStats(correct.toInt(), incorrect.toInt(), percentage)
    }
}

data class AnswersStats(val correct: Int, val incorrect: Int, val percentage: Float)