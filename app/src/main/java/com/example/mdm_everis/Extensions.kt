package com.example.mdm_everis


import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager

// Hide virtual keyboard
fun View.hideKeyboard(){
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(windowToken,0)
}

fun String.splitWithSpaceBefore() : String = this.substringBefore(" ", "Not found delimiter")

fun String.splitWithSpaceAfter() : String = this.substringAfter(" ","Not found delimiter")