package com.example.myfirstapp

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ViewModelUser : ViewModel() {
    private val mutLiveDataUser = MutableLiveData<DataClassUser>()

    fun setRegistrationData(user: DataClassUser) {
        mutLiveDataUser.value = user
    }

    fun combineWithPassword(password: String): DataClassUser? {
        val currentData = mutLiveDataUser.value
        return currentData?.copy(password = password)
    }
}