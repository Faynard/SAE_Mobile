package com.example.sae_mobile

import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import com.example.sae_mobile.Model.AnalyzedInstruction

class FragmentRecette : Fragment(R.layout.fragment_recette) {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_recette, container, false)

        val instruction = arguments?.getString("instruction","Aucune instruction pour cette recette")
        println("DANS FRAGMENT : $instruction")

        val button = view.findViewById<ImageButton>(R.id.cancelqzgfoiqgfiuqfluqlfhqlflo)
        val textInstructions = view.findViewById<TextView>(R.id.instructions)

        textInstructions.movementMethod = ScrollingMovementMethod()
        textInstructions.text = instruction

        button.setOnClickListener {
            println("TEST ICI")
            requireActivity().supportFragmentManager.popBackStack()
        }

        return view

    }
    companion object{
        @JvmStatic
        fun newInstance(instruction: String) = FragmentRecette().apply {
            arguments = Bundle().apply {
                println("DANS CREATION FRAGMENT : $instruction")
                putString("instruction", instruction)
            }
        }
    }
}