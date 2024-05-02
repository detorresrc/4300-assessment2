package com.detorresrc.tech4300.assessment2.lib

class QuestionManager {
    private var questions: List<Question> = listOf(
        Question("What is 2 + 2?", "4", QuestionType.ADDITION),
        Question("What is 1 + 2?", "3", QuestionType.ADDITION),
        Question("What is 5 - 3?", "2", QuestionType.SUBTRACTION),
        Question("What is 10 - 3?", "7", QuestionType.SUBTRACTION),
        Question("What is 4 * 3?", "12", QuestionType.MULTIPLICATION),
        Question("What is 4 * 1?", "4", QuestionType.MULTIPLICATION),
        Question("What is 10 / 2?", "5", QuestionType.DIVISION),
        Question("What is 15 / 5?", "3", QuestionType.DIVISION)
    )

    private var selectedQuestions: List<Question> = listOf()

    private var answers: List<Answer> = mutableListOf()

    fun generateRandomQuestions (questionCount: Int = 5) {
        selectedQuestions = questions.shuffled().take(questionCount)
    }

    fun getQuestions(): List<Question> {
        return selectedQuestions
    }

    fun addAnswer(answer: Answer) {
        (answers as MutableList).add(answer)
    }

    fun clearAnswer() {
        answers = mutableListOf()
    }

    fun getAnswers(): List<Answer> {
        return answers
    }
}