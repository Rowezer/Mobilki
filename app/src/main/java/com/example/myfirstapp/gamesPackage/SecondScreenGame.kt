package com.example.myfirstapp.gamesPackage

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import com.example.myfirstapp.R

class SecondScreenGame : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_second_screen_game, container, false)

        val btnFirstAns = view.findViewById<Button>(R.id.button_first_answer)
        btnFirstAns.setOnClickListener {
            Toast.makeText(context, "Incorrect answer!", Toast.LENGTH_SHORT).show()
        }

        val btnSecondAns = view.findViewById<Button>(R.id.button_second_answer)
        btnSecondAns.setOnClickListener {
            Toast.makeText(context, "That's right!", Toast.LENGTH_SHORT).show()
        }

        val btnThirdAns = view.findViewById<Button>(R.id.button_third_answer)
        btnThirdAns.setOnClickListener {
            Toast.makeText(context, "Incorrect answer!", Toast.LENGTH_SHORT).show()
        }

        val btnFourthAns = view.findViewById<Button>(R.id.button_fourth_answer)
        btnFourthAns.setOnClickListener {
            Toast.makeText(context, "Incorrect answer!", Toast.LENGTH_SHORT).show()
        }

        val btnNext = view.findViewById<Button>(R.id.button_next_answer)
        btnNext.setOnClickListener {
            Toast.makeText(context, "All questions were shown!", Toast.LENGTH_SHORT).show()
        }

        return view
    }
}