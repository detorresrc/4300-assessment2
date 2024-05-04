package com.detorresrc.tech4300.assessment2

import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.lifecycle.Lifecycle
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test

class MainActivityTest {
    private lateinit var scenario: ActivityScenario<MainActivity>

    @Before
    fun setUp() {
        scenario = ActivityScenario.launch(MainActivity::class.java)
        scenario.moveToState(Lifecycle.State.RESUMED)
    }

    @After
    fun tearDown() {
        scenario.close()
    }

    @Test
    fun checkIfActivityLaunched() {
        scenario.onActivity { activity ->
            assertNotNull(activity)
        }
    }

    @Test
    fun checkIfAllQuestionsDisplayedProperly() {
        scenario.onActivity { activity ->
            val questionManager = activity.getQuestionManager()

            assertNotNull(questionManager)
            assertEquals(5, questionManager.getQuestions().size)

            // Get All TextView element
            val questionLayout = activity.findViewById<LinearLayout>(R.id.questionContainer)
            assertNotNull(questionLayout)

            for (i in 0 until questionLayout.childCount) {
                // Get the CardView and the corresponding question
                val cardView = questionLayout.getChildAt(i) as CardView
                val question = questionManager.getQuestions()[i]

                val txtQuestion = cardView.findViewById<TextView>(R.id.question)
                assertEquals(question.question, txtQuestion.text.toString())

                val txtCategory = cardView.findViewById<TextView>(R.id.category)
                assertEquals(question.type.toString().uppercase(), txtCategory.text.toString())

                val txtSubhead = cardView.findViewById<TextView>(R.id.subhead)
                assertEquals("Question #${i + 1}", txtSubhead.text.toString())

                val answerEditText = cardView.findViewById<EditText>(R.id.answer)
                assertEquals("", answerEditText.text.toString())
            }
        }
    }

    @Test
    fun testValidation() {
        scenario.onActivity { activity ->
            val questionManager = activity.getQuestionManager()

            assertNotNull(questionManager)
            assertEquals(5, questionManager.getQuestions().size)

            // Get All TextView element
            val questionLayout = activity.findViewById<LinearLayout>(R.id.questionContainer)
            assertNotNull(questionLayout)

            questionLayout.getChildAt(0).findViewById<EditText>(R.id.answer).setText("")
            questionLayout.getChildAt(1).findViewById<EditText>(R.id.answer).setText("2")
            questionLayout.getChildAt(2).findViewById<EditText>(R.id.answer).setText("3")
            questionLayout.getChildAt(3).findViewById<EditText>(R.id.answer).setText("4")
            questionLayout.getChildAt(4).findViewById<EditText>(R.id.answer).setText("")
        }


        onView(withId(R.id.btnViewResult)).perform(click())
        onView(withText("Please answer all questions before viewing results."))
            .check(matches(isDisplayed()))
        onView(withText("OK")).perform(click())
    }

    @Test
    fun testRedirectionToResultActivity() {
        scenario.onActivity { activity ->
            val questionManager = activity.getQuestionManager()

            assertNotNull(questionManager)
            assertEquals(5, questionManager.getQuestions().size)

            // Get All TextView element
            val questionLayout = activity.findViewById<LinearLayout>(R.id.questionContainer)
            assertNotNull(questionLayout)

            questionLayout.getChildAt(0).findViewById<EditText>(R.id.answer).setText("1")
            questionLayout.getChildAt(1).findViewById<EditText>(R.id.answer).setText("2")
            questionLayout.getChildAt(2).findViewById<EditText>(R.id.answer).setText("3")
            questionLayout.getChildAt(3).findViewById<EditText>(R.id.answer).setText("4")
            questionLayout.getChildAt(4).findViewById<EditText>(R.id.answer).setText("5")
        }

        onView(withId(R.id.btnViewResult)).perform(click())
        onView(withText("YES")).perform(click())

        // Making sure if the result activity is launched
        onView(withText("RESULTS"))
            .check(
                matches(
                    isDisplayed()
                )
            )
    }

    @Test
    fun testReset() {
        scenario.onActivity { activity ->
            val questionManager = activity.getQuestionManager()

            assertNotNull(questionManager)
            assertEquals(5, questionManager.getQuestions().size)

            // Get All TextView element
            val questionLayout = activity.findViewById<LinearLayout>(R.id.questionContainer)
            assertNotNull(questionLayout)

            for (i in 0 until questionLayout.childCount)
                questionLayout.getChildAt(i).findViewById<EditText>(R.id.answer).setText((i+1).toString())
        }

        onView(withId(R.id.btnReset)).perform(click())
        onView(withText("YES")).perform(click())

        scenario.onActivity { activity ->
            // Get All TextView element
            val questionLayout = activity.findViewById<LinearLayout>(R.id.questionContainer)
            assertNotNull(questionLayout)

            for (i in 0 until questionLayout.childCount) {
                val answerEditText = questionLayout.getChildAt(i).findViewById<EditText>(R.id.answer)
                assertEquals("", answerEditText.text.toString())
            }
        }
    }
}