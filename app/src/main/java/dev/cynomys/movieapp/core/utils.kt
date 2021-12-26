package dev.cynomys.movieapp.core

import android.app.Activity
import android.content.Context
import android.widget.Toast
import androidx.fragment.app.Fragment

fun Activity.toast(context: Context, message: String, isLongToast: Boolean = false) {
    Toast.makeText(context, message, if (isLongToast) Toast.LENGTH_LONG else Toast.LENGTH_SHORT)
        .show()
}

fun Fragment.toast(context: Context, message: String, isLongToast: Boolean = false) {
    Toast.makeText(context, message, if (isLongToast) Toast.LENGTH_LONG else Toast.LENGTH_SHORT)
        .show()
}