package com.detorresrc.tech4300.assessment2

import com.detorresrc.tech4300.assessment2.lib.Question
import com.detorresrc.tech4300.assessment2.lib.QuestionType
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4


@RunWith(JUnit4::class)
class AnswerTest {

    @Test
    fun `Check isCorrect function, return true`() {
        val question = Question(
            "What is 2 + 2?",
            "4",
            QuestionType.ADDITION)

        assertEquals(true, question.isCorrect("4"))
    }

    @Test
    fun `Check isCorrect function, return false`() {
        val question = Question(
            "What is 2 + 2?",
            "1",
            QuestionType.ADDITION)

        assertEquals(false, question.isCorrect("4"))
    }
}
