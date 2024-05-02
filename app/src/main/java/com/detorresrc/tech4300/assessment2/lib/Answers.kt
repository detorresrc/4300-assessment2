package com.detorresrc.tech4300.assessment2.lib

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Answers(val answers: List<Answer>) : Parcelable {
}