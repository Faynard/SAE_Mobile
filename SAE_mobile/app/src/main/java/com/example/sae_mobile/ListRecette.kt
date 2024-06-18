package com.example.sae_mobile

import android.app.Activity
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



        cancel.setOnClickListener {
            finish()
        }

        val listRecettes = intent.getParcelableExtra("liste", Recipes::class.java)
        println("test "+listRecettes!!.recipes)

        var listRecettesAfficher = mutableListOf<String>()
        listRecettes.recipes.forEach { it ->

            listRecettesAfficher.add(it.title)
        }
        val aa = ArrayAdapter(this,android.R.layout.simple_list_item_1,listRecettesAfficher)
        listView.adapter = aa

    }


}