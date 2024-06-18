package com.example.sae_mobile

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageButton
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.sae_mobile.data.RecipeDAO
import com.example.sae_mobile.network.ApiService
import com.example.sae_mobile.viewmodel.RecipeViewModel
import com.example.sae_mobile.viewmodel.RecipeViewModelFactory
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.HttpTimeout

class MainActivity : AppCompatActivity() {

    lateinit var spinner: Spinner

    private val viewModel: RecipeViewModel by viewModels{
        val apiService = ApiService(kTorClient)
        val recipeDao = RecipeDAO(apiService)
        RecipeViewModelFactory(recipeDao)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fetchData()

        val titleSearch : TextView = findViewById(R.id.editTextText)
        val button = findViewById<Button>(R.id.btn_search)
        val portions: SeekBar = findViewById(R.id.seekBarPortion)
        val nbPortion : TextView = findViewById(R.id.id_txt_nombre_portion)
        var genreChoisi :String = ""

        portions.setOnSeekBarChangeListener(object : OnSeekBarChangeListener{
            override fun onStopTrackingTouch(p0: SeekBar?) {
                return
            }

            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                nbPortion.text = p0!!.progress.toString()
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
                return
            }

        })

        spinner = findViewById<Spinner>(R.id.dropdawn_genre)

        /*---------------------------------------------------Début Spinner--------------------------------------------------------------*/
        // Définir la liste des types de cuisines
        val cuisines = listOf(
            "",
            "African",
            "Asian",
            "American",
            "British",
            "Cajun",
            "Caribbean",
            "Chinese",
            "Eastern European",
            "European",
            "French",
            "German",
            "Greek",
            "Indian",
            "Irish",
            "Italian",
            "Japanese",
            "Jewish",
            "Korean",
            "Latin American",
            "Mediterranean",
            "Mexican",
            "Middle Eastern",
            "Nordic",
            "Southern",
            "Spanish",
            "Thai",
            "Vietnamese"
        )

        // Créer un adaptateur pour le Spinner
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, cuisines)

        // Spécifier le layout du dropdown
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        // Assigner l'adaptateur au Spinner
        spinner.adapter = adapter

        // Ajouter un écouteur d'événements de sélection
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                // Récupérer l'élément sélectionné
                genreChoisi = parent?.getItemAtPosition(position).toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                return// Code à exécuter lorsque rien n'est sélectionné
            }
        }

        /*---------------------------------------------------Fin Spinner--------------------------------------------------------------*/
        /*viewModel.recipes.observe(this, { recipes ->
            // Mettre à jour l'interface utilisateur avec les recettes
            println(recipes)
        })*/

        button.setOnClickListener {
            println("Paramètres : ${titleSearch.text.toString()}, ${genreChoisi}, ${portions.progress}")
            viewModel.fetchFilteredRecipes("ba43ca9e6c284df4aa230487f1cb1e53",titleSearch.text.toString(),genreChoisi,portions.progress)
            viewModel.filteredRecipes.observe(this) { recipes ->
                // Mettre à jour l'interface utilisateur avec les recettes
                println("Plats italiens : $recipes")
            }

        }
    }

    private fun fetchData() {
        viewModel.fetchRecipes("ba43ca9e6c284df4aa230487f1cb1e53")
    }

    companion object {
        val kTorClient = HttpClient(OkHttp) {
            install(HttpTimeout) {
                requestTimeoutMillis = 15000L
                connectTimeoutMillis = 15000L
                socketTimeoutMillis = 15000L
            }
        }
    }
}
