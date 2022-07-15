package com.example.appzaza.data.sharedpreferences

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import com.example.appzaza.ui.main.view.MainActivity
import com.example.appzaza.ui.main.view.login.LoginActivity
import kotlinx.coroutines.ExperimentalCoroutinesApi

@OptIn(ExperimentalCoroutinesApi::class)
class SharedPreference(val context: Context) {
    companion object {
        val PREFS_NAME = "SharedPreference"
        val IS_LOGIN: String = "isLogin"
        val KEY_NAME: String = "name"
        val KEY_PASS: String = "pass"
    }

    private val sharedPref: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    private val editor: SharedPreferences.Editor = sharedPref.edit()

    fun save(KEY_NAME: String, text: String) {
        editor.putString(KEY_NAME, text)
        editor.apply()
    }

    fun save(KEY_NAME: String, value: Int) {
        editor.putInt(KEY_NAME, value)
        editor.apply()
    }

    fun save(KEY_NAME: String, status: Boolean) {
        editor.putBoolean(KEY_NAME, status)
        editor.apply()
    }

    fun getValueString(KEY_NAME: String): String? {
        return sharedPref.getString(KEY_NAME, null)
    }

    fun getValueInt(KEY_NAME: String): Int {
        return sharedPref.getInt(KEY_NAME, 0)
    }

    fun getValueBoolean(KEY_NAME: String, defaultValue: Boolean): Boolean {
        return sharedPref.getBoolean(KEY_NAME, defaultValue)
    }

    fun clearSharedPreference() {
        editor.clear()
        editor.apply()
    }

    fun removeValue(KEY_NAME: String) {
        editor.remove(KEY_NAME)
        editor.apply()
    }

    fun createLoginSession(name: String, pass: String) {
        editor.putBoolean(IS_LOGIN, true)
        editor.putString(KEY_NAME, name)
        editor.putString(KEY_PASS, pass)
        editor.commit()
    }

    fun checkLogin() {

        if (!this.isLogIn()) {
            val i = Intent(context, MainActivity::class.java)
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            i.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
            context.startActivity(i)
        }
    }

    fun getUserDetails(): HashMap<String, String> {

        val user: Map<String, String> = HashMap<String, String>()
        (user as HashMap)[KEY_NAME] = sharedPref.getString(KEY_NAME, null).toString()
        user.put(KEY_PASS, sharedPref.getString(KEY_PASS, null).toString())

        return user
    }

    fun logoutUser() {
        editor.clear()
        editor.commit()

        val i = Intent(context, LoginActivity::class.java)
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        i.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        context.startActivity(i)
    }

    fun isLogIn(): Boolean {
        return sharedPref.getBoolean(IS_LOGIN, false)
    }

}
