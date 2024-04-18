package com.example.myfirstapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class LanguagesScreen : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_languages_screen, container, false)

        val recyclerView: RecyclerView = view.findViewById(R.id.rec_view_language)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = RecAdapterLanguages(getLanguagesList())

        return view
    }

    private fun getLanguagesList(): List<DataClassLanguage> {
        val languages: List<String> = listOf(
            "Russian", "English", "Chinese", "Belarus", "Kazakh")

        return languages.map { DataClassLanguage(it) }
    }
}