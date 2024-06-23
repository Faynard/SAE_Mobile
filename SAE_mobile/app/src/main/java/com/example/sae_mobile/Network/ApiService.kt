package com.example.sae_mobile.network


import com.example.sae_mobile.Model.Recipe
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromJsonElement
import kotlinx.serialization.json.jsonObject

class ApiService(private val client: HttpClient) {

    /* Fonction d'appel à l'api pour récupérer les informations souhaitées */
    suspend fun fetchFilteredRecipes(apiKey: String, name:String, genre:String, portion: Int,diets : String): List<Recipe> {
        val url = "https://api.spoonacular.com/recipes/complexSearch?addRecipeInstructions=true&addRecipeInformation=true&number=50"
        val response = client.get(url) {
            /* Différents paramètres que sélectionne l'utilisateur */
            parameter("apiKey", apiKey)
            parameter("query",name)
            parameter("cuisine",genre)
            parameter("minServings",portion)
            parameter("maxServings",portion)
            parameter("diet",diets) //String des régimes

        }
        val result = response.body<String>()
        val jsonElement = Json {
            ignoreUnknownKeys = true
            isLenient = true
        }.parseToJsonElement(result).jsonObject["results"]

        return if (jsonElement != null) {
            Json { ignoreUnknownKeys = true; isLenient = true }.decodeFromJsonElement(jsonElement)
        } else {
            emptyList()
        }
    }
}
