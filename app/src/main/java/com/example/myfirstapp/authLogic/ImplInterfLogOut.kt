package com.example.myfirstapp.authLogic

import android.content.Context
import com.example.myfirstapp.InterfLogOut
import com.example.myfirstapp.PREF_AUTH_NAME
import com.example.myfirstapp.USER_DATA_KEY

class ImplInterfLogOut : InterfLogOut {

    override fun clearOnBoardingFlag(context: Context) {
        val sharedPref = context.getSharedPreferences(PREF_COMPLETE_NAME, Context.MODE_PRIVATE)
        sharedPref.edit().clear().apply()
    }

    override fun clearAuthFlag(context: Context) {
        val sharedPref = context.getSharedPreferences(PREF_AUTH_NAME, Context.MODE_PRIVATE)
        sharedPref.edit().clear().apply()
    }

    override fun clearUserData(context: Context) {
        val sharedPref = context.getSharedPreferences(USER_DATA_KEY, Context.MODE_PRIVATE)
        sharedPref.edit().clear().apply()
    }
}