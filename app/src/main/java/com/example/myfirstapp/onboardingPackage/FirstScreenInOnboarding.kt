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

class FirstScreenInOnboarding : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_first_screen_in_onboarding, container, false)
        val userDataLogicImpl = ImplInterfData()

        val viewPager = activity?.findViewById<ViewPager2>(R.id.viewPager)
        val btnNext = view.findViewById<Button>(R.id.btn_next)
        btnNext.setOnClickListener {
            viewPager?.currentItem = 1
        }

        val textViewSkip = view.findViewById<TextView>(R.id.first_skip_text_view)
        textViewSkip.setOnClickListener {
            userDataLogicImpl.onBoardingCompleted(requireActivity())
            findNavController().navigate(R.id.action_viewPagerFragment_to_loginScreenFragment)
        }
        return view
    }

}