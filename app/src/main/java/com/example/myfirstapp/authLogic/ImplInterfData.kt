package com.example.myfirstapp.authLogic

import android.content.Context
import androidx.fragment.app.FragmentActivity
import com.example.myfirstapp.AUTHORIZED_STATE_KEY
import com.example.myfirstapp.DataClassUser
import com.example.myfirstapp.EMAIL_KEY
import com.example.myfirstapp.PASSWORD_KEY
import com.example.myfirstapp.PREF_AUTH_NAME
import com.example.myfirstapp.USER_DATA_KEY
import com.example.myfirstapp.USER_EMAIL
import com.example.myfirstapp.USER_FIRST_NAME
import com.example.myfirstapp.USER_IMAGE
import com.example.myfirstapp.USER_LAST_NAME
import com.example.myfirstapp.UserDataInterface
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.query.Columns
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.math.BigInteger
import java.security.MessageDigest
import java.util.regex.Pattern

const val PREF_COMPLETE_NAME = "onboarding_complete"
const val FINISHED_STATE_KEY = "finished_state"

const val SUPABASE_KEY = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImFmbmFxZ3hjamp3ZmFuZ292ZHZiIiwicm9sZSI6ImFub24iLCJpYXQiOjE3MTMyMTQ3ODksImV4cCI6MjAyODc5MDc4OX0.jMK7L2zLjQsHeHKlAoGJoR2Cxw0su8OqBqcQP0ZtXEQ"
const val PROJECT_URL = "https://afnaqgxcjjwfangovdvb.supabase.co"

class ImplInterfData : UserDataInterface {

    override fun registerNewUser(userModel: DataClassUser) {
        val supabaseClient = createSupabaseClient(
            supabaseUrl = PROJECT_URL,
            supabaseKey = SUPABASE_KEY
        ) {
            install(Postgrest)
        }
        GlobalScope.launch {
            supabaseClient.from("info_of_users").insert(userModel)
        }
    }

    override fun onBoardingCompleted(context: Context) {
        val sharedPref = context.getSharedPreferences(PREF_COMPLETE_NAME, Context.MODE_PRIVATE)
        sharedPref
            .edit()
            .putBoolean(FINISHED_STATE_KEY, true)
            .apply()
    }

    override fun isOnBoardingCompleted(requireActivity: FragmentActivity): Boolean {
        val sharedPref =
            requireActivity.getSharedPreferences(PREF_COMPLETE_NAME, Context.MODE_PRIVATE)
        return sharedPref.getBoolean(FINISHED_STATE_KEY, false)
    }

    override suspend fun getExistedUser(email: String, password: String): List<DataClassUser> =
        withContext(Dispatchers.IO) {
            val supabaseClient = createSupabaseClient(
                supabaseUrl = PROJECT_URL,
                supabaseKey = SUPABASE_KEY
            ) { install(Postgrest) }

            val existUser: List<DataClassUser> = async {
                supabaseClient.from("info_of_users")
                    .select(
                        columns = Columns.list(
                            "firstname",
                            "lastname",
                            "userImage",
                            "email",
                            "password",
                            "score"
                        )
                    ) {
                        filter {
                            eq("email", email)
                            eq("password", hashedPass(password))
                        }
                    }.decodeList<DataClassUser>()
            }.await()

            existUser
        }



    override fun makeAuthSharedFlag(context: Context, email: String, password: String) {
        val sharedPref = context.getSharedPreferences(PREF_AUTH_NAME, Context.MODE_PRIVATE)
        sharedPref
            .edit()
            .putBoolean(AUTHORIZED_STATE_KEY, true)
            .putString(EMAIL_KEY, email)
            .putString(PASSWORD_KEY, password)
            .apply()
    }

    override fun getAuthorizedEmailPass(requireActivity: FragmentActivity): List<String?> {
        val sharedPref = requireActivity.getSharedPreferences(PREF_AUTH_NAME, Context.MODE_PRIVATE)

        return listOf(
            sharedPref.getString(EMAIL_KEY, ""), sharedPref.getString(PASSWORD_KEY, "")
        )
    }

    override fun isAnyoneAuthorized(requireActivity: FragmentActivity): Boolean {
        val sharedPref = requireActivity.getSharedPreferences(PREF_AUTH_NAME, Context.MODE_PRIVATE)
        return sharedPref.getBoolean(AUTHORIZED_STATE_KEY, false)
    }

    override fun putDataUserProfileScreen(userModel: DataClassUser, context: Context) {
        val sharedPref = context.getSharedPreferences(USER_DATA_KEY, Context.MODE_PRIVATE)
        sharedPref
            .edit()
            .putString(USER_FIRST_NAME, userModel.firstname)
            .putString(USER_LAST_NAME, userModel.lastname)
            .putString(USER_EMAIL, userModel.email)
            .putString(USER_IMAGE, userModel.userImage)
            .apply()
    }

    override fun getDataUserProfileScreen(requireActivity: FragmentActivity): DataClassUser {
        requireActivity.getSharedPreferences(USER_DATA_KEY, Context.MODE_PRIVATE).apply {
            return DataClassUser(
                firstname = getString(USER_FIRST_NAME, "")!!,
                lastname = getString(USER_LAST_NAME, "")!!,
                email = getString(USER_EMAIL, "")!!,
                userImage = getString(USER_IMAGE, "")!!
            )
        }
    }


    override fun isValidData(data: String, needPattern: String): Boolean {
        val pattern = Pattern.compile(needPattern)
        return pattern.matcher(data).matches() && data.isNotEmpty()
    }

    override fun isEverythingOkay(userData: DataClassUser): Boolean {
        return isValidData(userData.firstname, USERNAME_PATTERN) &&
                isValidData(userData.lastname, USERNAME_PATTERN) &&
                isValidData(userData.email, EMAIL_PATTERN)
    }

    override fun hashedPass(input: String): String {
        val md = MessageDigest.getInstance("MD5")
        return BigInteger(1, md.digest(input.toByteArray())).toString(16).padStart(32, '0')
    }

    override suspend fun getTopUsers(): List<DataClassUser> =
        withContext(Dispatchers.IO) {
            val supabaseClient = createSupabaseClient(
                supabaseUrl = PROJECT_URL,
                supabaseKey = SUPABASE_KEY
            ) { install(Postgrest) }

            val userData: List<DataClassUser> = async {
                supabaseClient.from("info_of_users")
                    .select(
                        columns = Columns.list(
                            "firstname",
                            "lastname",
                            "userImage",
                            "email",
                            "password",
                            "score"
                        )
                    )
                    .decodeList<DataClassUser>()
            }.await()

            userData.sortedByDescending { it.score }.take(3)
        }

}