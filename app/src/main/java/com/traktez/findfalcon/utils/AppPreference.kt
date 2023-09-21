package com.traktez.findfalcon.utils

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import javax.inject.Inject

class AppPreference @Inject constructor(val context: Context) {
    private val sharedPref: SharedPreferences =
        context.getSharedPreferences(SHARED, Context.MODE_PRIVATE)
    private val editor: SharedPreferences.Editor = sharedPref.edit()


    var username: String
        get() = (sharedPref.getString(USER_NAME, "").toString())
        set(privacy) {
            editor.putString(USER_NAME, privacy)
            editor.commit()
        }

    fun clearPreference() {
        editor.clear()
        editor.apply()
    }


    companion object {
        const val USER_NAME = "user_name"
        @SuppressLint("StaticFieldLeak")
        private var appPreference: AppPreference? = null
        fun getInstance(context: Context): AppPreference? {
            synchronized(AppPreference::class.java) {
                if (appPreference == null) {
                    appPreference = AppPreference(context)
                }
            }
            return appPreference
        }

        private const val SHARED = "my_prefs"
    }
}