package com.detorresrc.tech4300.assessment2.lib

class QuestionManager(private val questions: List<Question>) {

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