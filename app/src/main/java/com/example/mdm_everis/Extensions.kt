package com.example.mdm_everis

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController



fun Fragment.navigateTo(@IdRes navActionResId: Int, bundle: Bundle, popUpTo: Int? = null, inclusive: Boolean = false) {
    try {
        if (popUpTo != null) {
            val navOptions = NavOptions.Builder()
                .setPopUpTo(popUpTo, inclusive)
                .setEnterAnim(R.anim.anim_enter_from_left)
                .setExitAnim(R.anim.anim_exit_to_left)
                .setPopEnterAnim(R.anim.anim_pop_from_right)
                .setPopExitAnim(R.anim.anim_pop_to_right)
                .build()

            findNavController().navigate(navActionResId, bundle, navOptions)
        } else {
            findNavController().navigate(navActionResId, bundle)
        }
    } catch (ignored: Exception) {
        // Navigation library has a bug which crash if same navActionResId is passed too quickly so we implemented this try / catch
    }
}


// Hide virtual keyboard
fun View.hideKeyboard(){
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(windowToken,0)
}
