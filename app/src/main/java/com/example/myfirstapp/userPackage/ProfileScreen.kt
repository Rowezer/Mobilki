package com.example.myfirstapp.userPackage

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.net.toUri
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.myfirstapp.DataClassUser
import com.example.myfirstapp.ImplInterfAvatar
import com.example.myfirstapp.R
import com.example.myfirstapp.authLogic.ImplInterfData
import com.example.myfirstapp.authLogic.ImplInterfLogOut
import kotlinx.coroutines.launch

class ProfileScreen : Fragment() {

    private lateinit var imageViewAvatar: ImageView
    private lateinit var userData: DataClassUser

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile_screen, container, false)
        val userDataLogicImpl = ImplInterfData()
        val textViewUsername = view.findViewById<TextView>(R.id.textView_username)
        val buttonLogOut = view.findViewById<Button>(R.id.button_logout)
        val buttonChangeAvatar = view.findViewById<Button>(R.id.button_change_avatar)
        val userImageImpl = ImplInterfAvatar()
        imageViewAvatar = view.findViewById(R.id.imageView_avatar)


        userData = userDataLogicImpl.getDataUserProfileScreen(requireActivity())

        textViewUsername.text = "${userData.firstname} ${userData.lastname}"
        if (userData.userImage.isNotEmpty()) {
            lifecycleScope.launch {
                val userImageBitmap = userImageImpl.getBitmapFromUri(userData.userImage.toUri())
                imageViewAvatar.scaleType = ImageView.ScaleType.CENTER_CROP
                imageViewAvatar.setImageBitmap(userImageBitmap)
            }

        }

        buttonChangeAvatar.setOnClickListener {
            pickImageFromGallery()
        }

        buttonLogOut.setOnClickListener {
            val logoutMethodsImpl = ImplInterfLogOut()

            logoutMethodsImpl.clearOnBoardingFlag(requireContext())
            logoutMethodsImpl.clearAuthFlag(requireContext())
            logoutMethodsImpl.clearUserData(requireContext())

            findNavController().navigate(R.id.action_userProfileFragment_to_loginScreenFragment)
        }

        return view
    }

    private fun pickImageFromGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/jpeg"
        launcher.launch(intent)
    }

    private val launcher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            val userImImpl = ImplInterfAvatar()
            if (result.resultCode == Activity.RESULT_OK && result.data != null) {
                val imageUri = result.data?.data
                val inputStream = context?.contentResolver?.openInputStream(imageUri!!)
                val byteArray = inputStream?.readBytes()
                lifecycleScope.launch {
                    val outputPath = userImImpl.loadImageUriToSupabase(
                        byteArray,
                        userData.email
                    )
                    val userImageBitmap = userImImpl.getBitmapFromUri(outputPath.toUri())
                    imageViewAvatar.setImageBitmap(userImageBitmap)
                }
            }
        }


}