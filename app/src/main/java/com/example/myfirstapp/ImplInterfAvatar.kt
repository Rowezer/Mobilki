package com.example.myfirstapp

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import com.example.myfirstapp.authLogic.PROJECT_URL
import com.example.myfirstapp.authLogic.SUPABASE_KEY
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.storage.Storage
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.storage.storage


class ImplInterfAvatar : InterfAvatar {

    override suspend fun loadImageUriToSupabase(userImage: ByteArray?, userEmail: String): String {
        val supabaseClient = createSupabaseClient(
            supabaseUrl = PROJECT_URL,
            supabaseKey = SUPABASE_KEY
        ) { install(Postgrest) }
        val supabaseStorage = createSupabaseClient(
            supabaseUrl = PROJECT_URL,
            supabaseKey = SUPABASE_KEY
        ) { install(Storage) }
        val bucket = supabaseStorage.storage.from("bucket_for_images")

        val outputPath =
            "avatars/" + userEmail.hashCode().toString() + "_" + userImage.hashCode() + ".jpg"
        bucket.upload(outputPath, userImage!!, upsert = false)

        supabaseClient.from("info_of_users").update(
            {
                set("userImage", outputPath)
            }
        ) {
            filter {
                eq("email", userEmail)
            }
        }
        return outputPath
    }

    override suspend fun getBitmapFromUri(imageUri: Uri): Bitmap {

        val supabaseStorage = createSupabaseClient(
            supabaseUrl = PROJECT_URL,
            supabaseKey = SUPABASE_KEY
        ) { install(Storage) }

        val bucket = supabaseStorage.storage.from("bucket_for_images")
        val bytes = bucket.downloadAuthenticated(imageUri.toString())

        return BitmapFactory.decodeStream(bytes.inputStream())
    }
}