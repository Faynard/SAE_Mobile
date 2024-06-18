package com.example.sae_mobile

import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
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

    private val viewModel: RecipeViewModel by viewModels{
        val apiService = ApiService(kTorClient)
        val recipeDao = RecipeDAO(apiService)
        RecipeViewModelFactory(recipeDao)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fetchData()

        val button = findViewById<Button>(R.id.btn_search)

        /*viewModel.recipes.observe(this, { recipes ->
            // Mettre à jour l'interface utilisateur avec les recettes
            println(recipes)
        })*/

        button.setOnClickListener {
            viewModel.fetchFilteredRecipes("ba43ca9e6c284df4aa230487f1cb1e53","italian")
            println("plat italiens")
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
