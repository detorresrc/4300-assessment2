package com.detorresrc.tech4300.assessment2

import com.detorresrc.tech4300.assessment2.lib.Answer
import com.detorresrc.tech4300.assessment2.lib.Question
import com.detorresrc.tech4300.assessment2.lib.QuestionManager
import com.detorresrc.tech4300.assessment2.lib.QuestionType
import org.junit.Assert.*
import org.junit.Before

import org.junit.Test
import org.mockito.Mockito.spy
import org.mockito.Mockito.`when`

class QuestionManagerTest {
    private var questions: List<Question> = listOf()

    @Before
    fun setUp() {
        questions = listOf(
            Question("What is 2 + 2?", "4", QuestionType.ADDITION),
            Question("What is 1 + 2?", "3", QuestionType.ADDITION),
            Question("What is 5 - 3?", "2", QuestionType.SUBTRACTION),
            Question("What is 10 - 3?", "7", QuestionType.SUBTRACTION),
            Question("What is 4 * 3?", "12", QuestionType.MULTIPLICATION),
            Question("What is 4 * 1?", "4", QuestionType.MULTIPLICATION),
            Question("What is 10 / 2?", "5", QuestionType.DIVISION),
            Question("What is 15 / 5?", "3", QuestionType.DIVISION)
        )
    }

    @Test
    fun generateRandomQuestions() {
        val questionManager = QuestionManager(questions)
        val spyQuestionManager = spy(questionManager)
        val totalQuestions = 5

        `when`(spyQuestionManager.generateRandomQuestions(totalQuestions)).then {
            assertEquals(totalQuestions, spyQuestionManager.getQuestions().size)
        }
        spyQuestionManager.generateRandomQuestions(totalQuestions)
    }

    @Test
    fun getQuestions() {
        val questionManager = QuestionManager(questions)
        questionManager.generateRandomQuestions()

        assertEquals(5, questionManager.getQuestions().size)
    }

    @Test
    fun clearAnswer() {
        val questionManager = QuestionManager(questions)
        val spyQuestionManager = spy(questionManager)

        assertEquals(0, spyQuestionManager.getAnswers().size)
        spyQuestionManager.addAnswer(Answer(Question("Question #1", "#1", QuestionType.ADDITION), "#1"))
        assertEquals(1, spyQuestionManager.getAnswers().size)

        `when`(spyQuestionManager.clearAnswer()).then {
            assertEquals(0, spyQuestionManager.getAnswers().size)
        }

        spyQuestionManager.clearAnswer()
    }

    @Test
    fun addAnswer() {
        val questionManager = QuestionManager(questions)

        assertEquals(0, questionManager.getAnswers().size)
        questionManager.addAnswer(Answer(Question("Question #1", "#1", QuestionType.ADDITION), "#1"))
        assertEquals(1, questionManager.getAnswers().size)
    }
}