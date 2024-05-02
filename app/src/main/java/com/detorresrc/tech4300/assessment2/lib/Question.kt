package com.detorresrc.tech4300.assessment2.lib

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Question(val question: String, val correctAnswer: String, val type: QuestionType) :
    Parcelable {

    fun isCorrect(answer: String): Boolean {
        return answer.trim().lowercase() == correctAnswer.trim().lowercase()
    }
}