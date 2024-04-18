package com.example.myfirstapp.onboardingPackage

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import com.example.myfirstapp.R
import com.example.myfirstapp.authLogic.ImplInterfData

class ThirdScreenInOnboarding : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_third_screen_in_onboarding, container, false)
        val userDataLogicImpl = ImplInterfData()

        val btnFinish = view.findViewById<Button>(R.id.btn_choose_lang)
        btnFinish.setOnClickListener {
            userDataLogicImpl.onBoardingCompleted(requireActivity())
            findNavController().navigate(R.id.action_viewPagerFragment_to_chooseLanguage)
        }

        return view
    }
}