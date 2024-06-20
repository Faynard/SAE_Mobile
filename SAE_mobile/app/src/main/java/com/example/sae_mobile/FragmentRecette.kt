package com.example.sae_mobile

import android.os.Bundle
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

        val button = view.findViewById<ImageButton>(R.id.cancelqzgfoiqgfiuqfluqlfhqlflo)
        val textInstructions = view.findViewById<TextView>(R.id.instructions)
        arguments?.getString("instructions")
        textInstructions.text

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
                putString("instruction", instruction)
            }
        }
    }
}