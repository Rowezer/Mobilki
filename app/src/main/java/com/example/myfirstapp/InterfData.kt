package com.example.myfirstapp

import android.content.Context
import androidx.fragment.app.FragmentActivity
import com.example.myfirstapp.DataClassUser

const val PREF_AUTH_NAME = "auth_completed"
const val AUTHORIZED_STATE_KEY = "is_authorized"
const val EMAIL_KEY = "authorized_email"
const val PASSWORD_KEY = "authorized_pass"

const val USER_DATA_KEY = "user_data"
const val USER_FIRST_NAME = "user_first_name"
const val USER_LAST_NAME = "user_last_name"
const val USER_EMAIL = "user_email"
const val USER_IMAGE = "user_image"


interface UserDataInterface {
    suspend fun getExistedUser(email: String, password: String = ""): List<DataClassUser>

    fun isValidData(data: String, needPattern: String): Boolean
    fun isEverythingOkay(userData: DataClassUser): Boolean

    fun onBoardingCompleted(context: Context)

    fun makeAuthSharedFlag(context: Context, email: String, password: String)
    fun getAuthorizedEmailPass(requireActivity: FragmentActivity): List<String?>
    fun registerNewUser(userModel: DataClassUser)

    fun putDataUserProfileScreen(userModel: DataClassUser, context: Context)
    fun getDataUserProfileScreen(requireActivity: FragmentActivity): DataClassUser

    fun isOnBoardingCompleted(requireActivity: FragmentActivity): Boolean
    fun isAnyoneAuthorized(requireActivity: FragmentActivity): Boolean

    fun hashedPass(input: String): String

    suspend fun getTopUsers(): List<DataClassUser>
}