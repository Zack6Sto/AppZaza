package com.example.appzaza.util

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager

object KeyboardUtil {
    fun hideKeyboard(Ac: Activity?) {
        if (Ac != null) {
            var v = Ac.currentFocus
            if (v == null) {
                val w = Ac.window
                v = w.currentFocus
                if (v == null) {
                    v = w.decorView
                }
                v.clearFocus()
            }
            hideKeyboard(v)
        }
    }

//    fun showKeyboard(Ac: Activity?) {
//        val v: View = ActivityUtil.getCurrentViewFromActivity(Ac)
//        if (v != null) {
//            showKeyboard(v)
//        }
//    }

    fun hideKeyboard(v: View?) {
        if (v != null) {
            val imm = getKeyboardManager(v.context)
            if (imm != null) {
                val token = v.windowToken
                if (!imm.hideSoftInputFromWindow(token, 0)) {
                    imm.hideSoftInputFromInputMethod(token, InputMethodManager.HIDE_NOT_ALWAYS)
                }
            }
        }
    }

    fun showKeyboard(v: View?) {
        if (v != null) {
            val imm = getKeyboardManager(v.context)
            if (imm != null) {
                if (!imm.showSoftInput(v, InputMethodManager.SHOW_FORCED)) {
                    val token = v.windowToken
                    imm.showSoftInputFromInputMethod(token, 0)
                }
            }
        }
    }

    fun getKeyboardManager(Ac: Activity): InputMethodManager? {
        val obj = Ac.getSystemService(Context.INPUT_METHOD_SERVICE)
        return if (obj != null) obj as InputMethodManager else null
    }

    private fun getKeyboardManager(Ac: Context): InputMethodManager? {
        val obj = Ac.getSystemService(Context.INPUT_METHOD_SERVICE)
        return if (obj != null) obj as InputMethodManager else null
    }
}