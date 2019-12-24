package com.example.mdm_everis


import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import java.text.SimpleDateFormat
import java.util.*

// Hide virtual keyboard
fun View.hideKeyboard(){
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(windowToken,0)
}

fun String.splitWithSpaceBefore() : String = this.substringBefore(" ", "Not found delimiter")

fun String.splitWithSpaceAfter() : String = this.substringAfter(" ","Not found delimiter")

fun String.stringDateToLong(format : String) : Long {
    val f = SimpleDateFormat(format, Locale.getDefault())
    val date = f.parse(this)
    return date.time
}

fun Long.convertLongToDate(format : String): String {
    val d = Date(this)
    val f = SimpleDateFormat(format, Locale.getDefault())
    return f.format(d)
}