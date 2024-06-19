package com.example.sae_mobile

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.sae_mobile.Model.Recipe

class RecetteChoisie : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.recette_choix)

        val recetteChoisi = intent.getParcelableExtra("recipe", Recipe::class.java)

        val title : TextView = findViewById(R.id.nomRecette)
        val portions : TextView = findViewById(R.id.nbPortion)
        val imageRecette : ImageView = findViewById(R.id.imageRecette)

        title.text = recetteChoisi!!.title
        portions.text = recetteChoisi!!.servings.toString()

        Glide.with(this)
            .load(recetteChoisi!!.sourceUrl)
            .into(imageRecette)

    }
}