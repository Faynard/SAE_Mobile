package com.example.sae_mobile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton

class FragmentRecette : Fragment(R.layout.fragment_recette) {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_recette, container, false)

        val button = view.findViewById<ImageButton>(R.id.cancelqzgfoiqgfiuqfluqlfhqlflo)

        button.setOnClickListener {
            println("TEST ICI")
            requireActivity().supportFragmentManager.popBackStack()
        }

        return super.onCreateView(inflater, container, savedInstanceState)

    }

    companion object{
        @JvmStatic
        fun newInstance() = FragmentRecette()
    }
}