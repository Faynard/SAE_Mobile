package com.example.sae_mobile

import android.app.Activity
import android.content.Intent
import android.media.Image
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ImageButton
import android.widget.ListView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.sae_mobile.Model.Recipe
import com.example.sae_mobile.Model.Recipes

class ListRecette : AppCompatActivity() {

    lateinit var cancel : ImageButton
    lateinit var listView: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.list_recette)

        cancel = findViewById(R.id.id_cancel_button)
        listView = findViewById(R.id.id_liste_recette)


        /* Bouton Cancel */
        cancel.setOnClickListener {
            finish()
        }

        val listRecettes = intent.getParcelableExtra("liste", Recipes::class.java)

        var listRecettesAfficher = mutableListOf<String>()

        /* Récupération uniquement des titres des recettes pour affichage */
        listRecettes!!.recipes.forEach { it ->

            listRecettesAfficher.add(it.title)
        }

        /* Utilisation de ArrayAdapter pour afficher les éléments */
        val aa = ArrayAdapter(this,android.R.layout.simple_list_item_1,listRecettesAfficher)
        listView.adapter = aa

        /* Récupère la recette cliquée et l'envoie en object simple a l'activité suivante RecetteChoisie avec un Intent()*/
        listView.setOnItemClickListener{_,_,i,_ -> aa.getItem(i)
            // Récupérer l'objet Recipe correspondant à l'index cliqué
            val selectedRecipe = listRecettes.recipes[i]
            println(selectedRecipe)
            val intent = Intent(this@ListRecette, RecetteChoisie::class.java)
            intent.putExtra("recipe", selectedRecipe)
            startActivity(intent)
        }

    }


}