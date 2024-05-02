package com.detorresrc.tech4300.assessment2

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.detorresrc.tech4300.assessment2.lib.Answers

class ResultActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_result)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(
                v.paddingLeft + systemBars.left,
                v.paddingTop + systemBars.top,
                v.paddingRight + systemBars.right,
                v.paddingBottom + systemBars.bottom
            )
            insets
        }

        if (intent != null) {
            var answers : Answers? = null
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                answers = intent.getParcelableExtra("answers", Answers::class.java)
            } else {
                @Suppress("DEPRECATION")
                answers = intent.getParcelableExtra("answers")
            }
            setupAnswers(answers)
        }

    }

    private fun setupAnswers(answersObject: Answers?) {
        // Get the ConstraintLayout that will contain the questions
        val questionLayout = findViewById<LinearLayout>(R.id.questionContainer)
        // Get the LayoutInflater instance to inflate the views
        val layoutInflater = LayoutInflater.from(this)

        var previousCardViewId = View.NO_ID
        answersObject?.answers?.forEachIndexed { index, answer ->
            Log.d("ResultActivity::setupAnswers", "Answer: $answer")

            val cardView = layoutInflater.inflate(R.layout.question_result_layout, questionLayout, false) as CardView

            val subhead = cardView.findViewById<TextView>(R.id.subhead)
            val questionText = cardView.findViewById<TextView>(R.id.question)
            val categoryText = cardView.findViewById<TextView>(R.id.category)
            val answerText = cardView.findViewById<TextView>(R.id.answer)
            val iconCorrect = cardView.findViewById<ImageView>(R.id.iconCorrect)
            val iconWrong = cardView.findViewById<ImageView>(R.id.iconWrong)
            val correctAnswer = cardView.findViewById<TextView>(R.id.correctAnswer)

            // Set the text for the TextViews
            subhead.text = "Question #${index + 1}"
            questionText.text = answer.question.question
            categoryText.text = answer.question.type.toString().uppercase()
            answerText.text = answer.answer
            correctAnswer.text = "The correct answer is : ${answer.question.correctAnswer}"

            if (answer.isCorrect())
                iconCorrect.visibility = View.VISIBLE
            else
                iconWrong.visibility = View.VISIBLE

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

            // Update the ID of the previous CardView
            previousCardViewId = cardView.id
        }
    }
}