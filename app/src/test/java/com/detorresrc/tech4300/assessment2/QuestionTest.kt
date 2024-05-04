package com.detorresrc.tech4300.assessment2

import com.detorresrc.tech4300.assessment2.lib.Question
import com.detorresrc.tech4300.assessment2.lib.QuestionType
import org.junit.Assert.*

import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class QuestionTest {

    @Test
    fun withCorrectAnswer() {
        val question = Question("What is 2 + 2?", "4", QuestionType.ADDITION)

        assertTrue(question.isCorrect("4"))
        assertTrue(question.isCorrect(" 4 "))
        assertTrue(question.isCorrect("4 "))
        assertTrue(question.isCorrect(" 4"))
        assertTrue(question.isCorrect("4".toUpperCase()))
        assertTrue(question.isCorrect("4".toLowerCase()))
        assertTrue(question.isCorrect(" 4 ".toUpperCase()))
        assertTrue(question.isCorrect(" 4 ".toLowerCase()))

        assertFalse(question.isCorrect("5"))
        assertFalse(question.isCorrect(" 5 "))
        assertFalse(question.isCorrect("5 "))
        assertFalse(question.isCorrect(" 5"))
        assertFalse(question.isCorrect("5".toUpperCase()))
        assertFalse(question.isCorrect("5".toLowerCase()))
        assertFalse(question.isCorrect(" 5 ".toUpperCase()))
        assertFalse(question.isCorrect(" 5 ".toLowerCase()))
    }

}