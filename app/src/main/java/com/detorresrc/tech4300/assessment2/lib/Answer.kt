package com.detorresrc.tech4300.assessment2.lib

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Answer(val question: Question, val answer: String) : Parcelable {

    fun isCorrect(): Boolean {
        return question.isCorrect(answer)
    }
}