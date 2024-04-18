package com.example.myfirstapp

import android.content.Context

interface InterfLogOut {
    fun clearOnBoardingFlag(context: Context)
    fun clearAuthFlag(context: Context)
    fun clearUserData(context: Context)
}