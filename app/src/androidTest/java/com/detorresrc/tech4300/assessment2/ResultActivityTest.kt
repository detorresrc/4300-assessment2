package com.detorresrc.tech4300.assessment2

import android.content.Intent
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.view.isVisible
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.detorresrc.tech4300.assessment2.lib.Answer
import com.detorresrc.tech4300.assessment2.lib.Answers
import com.detorresrc.tech4300.assessment2.lib.Question
import com.detorresrc.tech4300.assessment2.lib.QuestionType
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import java.util.regex.Pattern.matches

class ResultActivityTest {
    private lateinit var scenario: ActivityScenario<ResultActivity>
    private lateinit var answers: Answers

    @Before
    fun setUp() {
        val question1 = Question(
            "1+1",
            "2",
            QuestionType.ADDITION
        )
        val question2 = Question(
            "2+2",
            "4",
            QuestionType.ADDITION
        )
        val question3 = Question(
            "2-2",
            "0",
            QuestionType.SUBTRACTION
        )
        val question4 = Question(
            "2*2",
            "4",
            QuestionType.MULTIPLICATION
        )
        val question5 = Question(
            "5/5",
            "1",
            QuestionType.DIVISION
        )

        this.answers = Answers(listOf(
            Answer(question1, "2"),
            Answer(question2, "4"),
            Answer(question3, "1"),
            Answer(question4, "1"),
            Answer(question5, "1"),
        ))

        val intent = Intent(ApplicationProvider.getApplicationContext(), ResultActivity::class.java)
        intent.putExtra("answers", this.answers)

        scenario = ActivityScenario.launch(intent)
    }

    @After
    fun tearDown() {
    }

    @Test
    fun checkIfActivityLaunched() {
        Espresso.onView(ViewMatchers.withText("RESULTS"))
            .check(
                ViewAssertions.matches(
                    ViewMatchers.isDisplayed()
                )
            )
    }

    @Test
    fun checkIfAllQuestionsAndAnswersDisplayedProperly() {
        scenario.onActivity { activity ->
            val questionLayout = activity.findViewById<LinearLayout>(R.id.questionContainer)
            assertNotNull(questionLayout)

            for (i in 0 until questionLayout.childCount) {
                val answer = this.answers.answers[i]

                val cardView = questionLayout.getChildAt(i) as CardView
                val txtQuestion = cardView.findViewById<TextView>(R.id.question)
                assertEquals(answer.question.question, txtQuestion.text.toString())

                val txtCategory = cardView.findViewById<TextView>(R.id.category)
                assertEquals(answer.question.type.toString().uppercase(), txtCategory.text.toString())

                val txtSubhead = cardView.findViewById<TextView>(R.id.subhead)
                assertEquals("Question #${i + 1}", txtSubhead.text.toString())

                val answerEditText = cardView.findViewById<TextView>(R.id.answer)
                assertEquals(answer.answer, answerEditText.text.toString())

                val iconCorrect = cardView.findViewById<ImageView>(R.id.iconCorrect)
                val iconIncorrect = cardView.findViewById<ImageView>(R.id.iconWrong)

                if(answer.isCorrect()){
                    assertEquals(iconCorrect.isVisible, true)
                    assertEquals(iconIncorrect.isVisible, false)
                }else{
                    assertEquals(iconCorrect.isVisible, false)
                    assertEquals(iconIncorrect.isVisible, true)
                }
            }
        }
    }

    @Test
    fun checkIfScoreIsCorrect() {
        onView(withId(R.id.totalCorrectAnswer)).check(
            ViewAssertions.matches(
                withText("3")
            )
        )
        onView(withId(R.id.totalIncorrectAnswer)).check(
            ViewAssertions.matches(
                withText("2")
            )
        )
        onView(withId(R.id.percentage)).check(
            ViewAssertions.matches(
                withText("60.00")
            )
        )
    }
}