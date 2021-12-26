package dev.cynomys.movieapp.view.activities

import androidx.appcompat.app.AppCompatActivity
import android.view.View
import android.widget.ProgressBar
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import dev.cynomys.movieapp.R

open class BaseActivity : AppCompatActivity() {
    private lateinit var progresBar: ProgressBar
    fun attachProgressBar(constraintLayout: ConstraintLayout) {
        progresBar = layoutInflater.inflate(R.layout.custom_progress_bar, null) as ProgressBar

        progresBar.apply {
            val constraintLayoutParams = ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.WRAP_CONTENT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT
            )
            constraintLayoutParams.apply {
                bottomToBottom = ConstraintSet.PARENT_ID
                endToEnd = ConstraintSet.PARENT_ID
                startToStart = ConstraintSet.PARENT_ID
                topToTop = ConstraintSet.PARENT_ID
            }

            layoutParams = constraintLayoutParams
        }

        constraintLayout.addView(progresBar)
    }

    fun hideProgressBar() {
        progresBar.visibility = View.GONE
    }

    fun showProgressBar() {
        progresBar.visibility = View.VISIBLE
    }
}