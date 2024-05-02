package com.detorresrc.tech4300.assessment2

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.detorresrc.tech4300.assessment2.lib.Answer
import com.detorresrc.tech4300.assessment2.lib.Answers
import com.detorresrc.tech4300.assessment2.lib.QuestionManager

class MainActivity : AppCompatActivity() {
    private val questionManager = QuestionManager()
    private var btnReset: Button? = null
    private var btnViewResults: Button? = null
    private var viewedResult: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        // Call the superclass method
        super.onCreate(savedInstanceState)

        // Enable edge-to-edge display
        enableEdgeToEdge()

        // Set the layout for this activity
        setContentView(R.layout.activity_main)

        // Set an OnApplyWindowInsetsListener to adjust padding for system bars
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            // Get the insets for the system bars
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())

            // Adjust the padding of the view
            v.setPadding(
                v.paddingLeft + systemBars.left,
                v.paddingTop + systemBars.top,
                v.paddingRight + systemBars.right,
                v.paddingBottom + systemBars.bottom
            )

            // Return the insets
            insets
        }

        // Setup the questions for the quiz
        setupQuestions()

        // Get the reset button and set its click listener
        btnReset = findViewById(R.id.btnReset)
        btnReset?.setOnClickListener {
            // Create an AlertDialog to confirm the reset action
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Assessment 2")
                .setMessage("Are you sure you want to reset the questions?")
                .setPositiveButton("Yes") { _, _ ->
                    // If the user confirms, reset the questions
                    setupQuestions()
                    viewedResult = false
                }
                .setNegativeButton("No") { dialog, _ ->
                    // If the user cancels, dismiss the dialog
                    dialog.dismiss()
                }
                .create().show()
        }

        // Get the view results button and set its click listener
        btnViewResults = findViewById(R.id.btnViewResult)
        btnViewResults?.setOnClickListener {
            // Validate the answers
            if(!getAndValidateAnswer()) {
                // If the answers are not valid, show an AlertDialog
                var builder = AlertDialog.Builder(this)
                    .setTitle("Assessment 2")
                    .setMessage("Please answer all questions before viewing results.")
                    .setNegativeButton("OK") { dialog, _ ->
                        // Dismiss the dialog
                        dialog.dismiss()
                    }
                    .create().show()
            }else {
                Log.d("MainActivity", "Viewing results : $viewedResult")
                if(viewedResult) {
                    viewResult()
                }else{
                    val builder = AlertDialog.Builder(this)
                    builder.setTitle("Assessment 2")
                        .setMessage("Are you sure you want to view the result? You wont be able to change your answers.")
                        .setPositiveButton("Yes") { _, _ ->
                            disableAnswerEditTexts()
                            viewedResult = true

                            viewResult()
                        }
                        .setNegativeButton("No") { dialog, _ ->
                            // If the user cancels, dismiss the dialog
                            dialog.dismiss()
                        }
                        .create().show()
                }
            }
        }
    }

    private fun viewResult() {
        // If the answers are valid, start the ResultActivity
        val intent = Intent(this, ResultActivity::class.java)
        intent.putExtra("answers", Answers(questionManager.getAnswers()))
        startActivity(intent)
    }

    @SuppressLint("SetTextI18n")
    private fun setupQuestions() {
        // Get the ConstraintLayout that will contain the questions
        val questionLayout = findViewById<LinearLayout>(R.id.questionContainer)
        // Get the LayoutInflater instance to inflate the views
        val layoutInflater = LayoutInflater.from(this)

        // Remove all existing views from the layout
        questionLayout.removeAllViews()

        // Generate random questions
        questionManager.generateRandomQuestions(5)
        // Loop through each question
        questionManager.getQuestions().forEachIndexed { index, question ->
            // Inflate the question layout and add it to the ConstraintLayout
            val cardView = layoutInflater.inflate(R.layout.question_layout, questionLayout, false) as CardView

            // Get the TextViews from the CardView
            val subhead = cardView.findViewById<TextView>(R.id.subhead)
            val questionText = cardView.findViewById<TextView>(R.id.question)
            val categoryText = cardView.findViewById<TextView>(R.id.category)

            // Set the text for the TextViews
            subhead.text = "Question #${index + 1}"
            questionText.text = question.question
            categoryText.text = question.type.toString().uppercase()

            // Create layout parameters for the CardView
            val layoutParams = LinearLayout.LayoutParams(
                ConstraintLayout.LayoutParams.MATCH_PARENT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT
            )

            // Add margin to the CardView
            val margin = resources.getDimensionPixelSize(R.dimen.cardview_margin)
            layoutParams.setMargins(margin, margin, margin, margin)

            // Set the layout parameters to the CardView
            cardView.layoutParams = layoutParams
            // Add the CardView to the ConstraintLayout
            questionLayout.addView(cardView)
        }
    }

    private fun getAndValidateAnswer() : Boolean {
        // Get the ConstraintLayout that contains the questions
        val questionLayout = findViewById<LinearLayout>(R.id.questionContainer)

        // Clear the previous answers
        questionManager.clearAnswer()

        var isValid = true
        // Loop through each child view of the ConstraintLayout
        for (i in 0 until questionLayout.childCount) {
            // Get the CardView and the corresponding question
            val cardView = questionLayout.getChildAt(i) as CardView
            val question = questionManager.getQuestions()[i]
            // Get the EditText that contains the user's answer
            val answerEditText = cardView.findViewById<EditText>(R.id.answer)
            val answer = answerEditText.text.toString()

            // Log the question and the answer
            Log.d("MainActivity", "Question: '${question.question}', Answer: '$answer'")

            // If the answer is blank, set the background of the EditText to an error drawable and mark as invalid
            if (answer.trim().isBlank()) {
                answerEditText.setBackgroundResource(R.drawable.field_validation_error)
                isValid = false
            } else {
                // If the answer is not blank, clear the background of the EditText and add the answer to the QuestionManager
                answerEditText.setBackgroundResource(R.drawable.underline_text_bg)
                questionManager.addAnswer(Answer(question, answer))
            }
        }

        // Return whether all answers are valid
        return isValid
    }

    // Disable all Answer EditTexts
    private fun disableAnswerEditTexts() {
        // Get the ConstraintLayout that contains the questions
        val questionLayout = findViewById<LinearLayout>(R.id.questionContainer)

        // Loop through each child view of the ConstraintLayout
        for (i in 0 until questionLayout.childCount) {
            // Get the CardView
            val cardView = questionLayout.getChildAt(i) as CardView
            // Get the EditText that contains the user's answer
            val answerEditText = cardView.findViewById<EditText>(R.id.answer)

            // Disable the EditText
            answerEditText.isEnabled = false
        }
    }
}