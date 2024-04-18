package com.example.myfirstapp.onboardingPackage

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.example.myfirstapp.R
import com.example.myfirstapp.authLogic.ImplInterfData

class SecondScreenInOnboarding : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_second_screen_in_onboarding, container, false)
        val viewPager = activity?.findViewById<ViewPager2>(R.id.viewPager)
        val userDataLogicImpl = ImplInterfData()

        val btnMore = view.findViewById<Button>(R.id.btn_choose_lang)
        btnMore.setOnClickListener {
            viewPager?.currentItem = 2
        }

        val textViewSkip = view.findViewById<TextView>(R.id.second_skip_text_view)
        textViewSkip.setOnClickListener {
            userDataLogicImpl.onBoardingCompleted(requireActivity())
            findNavController().navigate(R.id.action_viewPagerFragment_to_loginScreenFragment)
        }

        return view
    }
}