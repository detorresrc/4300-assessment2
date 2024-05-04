package com.detorresrc.tech4300.assessment2

import com.detorresrc.tech4300.assessment2.lib.Answer
import com.detorresrc.tech4300.assessment2.lib.Answers
import com.detorresrc.tech4300.assessment2.lib.Question
import com.detorresrc.tech4300.assessment2.lib.QuestionType
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class AnswersTest {
    private lateinit var answers: Answers

    @Before
    fun setUp() {
        // Create a new Answer object with a Question object and an answer
        val answer1 = Answer(Question("What is 2 + 2?", "4", QuestionType.ADDITION), "4")
        val answer2 = Answer(Question("What is 2 + 3?", "5", QuestionType.ADDITION), "5")
        val answer3 = Answer(Question("What is 2 + 2?", "4", QuestionType.ADDITION), "1")

        answers = Answers(listOf(answer1, answer2, answer3))
    }

    @Test
    fun withCorrectStats() {
        // Test if the correct answers are counted
        assertEquals(2, answers.getStats().correct)

        // Test if the incorrect answers are counted
        assertEquals(1, answers.getStats().incorrect)

        // Test if the percentage is calculated correctly
        assertEquals(66.66667f, answers.getStats().percentage)
    }
}