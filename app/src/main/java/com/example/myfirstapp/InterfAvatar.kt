package com.example.myfirstapp

import android.graphics.Bitmap
import android.net.Uri

interface InterfAvatar {
    suspend fun loadImageUriToSupabase(userImage: ByteArray?, userEmail: String): String
    suspend fun getBitmapFromUri(imageUri: Uri): Bitmap
}