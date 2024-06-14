package com.example.sae_mobile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.client.HttpClient
import android.util.Log
import android.widget.Button
import com.example.sae_mobile.Model.Recipe
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromJsonElement
import kotlinx.serialization.json.jsonObject

class MainActivity : AppCompatActivity() {

    lateinit var recipeList : List<Recipe>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        val button = findViewById<Button>(R.id.btn_get)
        button.setOnClickListener {
            /*println(getRequestResult("https://api.spoonacular.com/recipes/complexSearch"))*/


            fetchData("https://api.spoonacular.com/recipes/complexSearch?addRecipeInformation=true")
        }
    }

    private fun fetchData(url: String) {

        GlobalScope.launch(Dispatchers.IO) {

            val response = kTorClient.get(url) {
                parameter("apiKey", "ba43ca9e6c284df4aa230487f1cb1e53")
            }
            Log.d("MainActivity", "Response: $response")
            val result = response.body<String>()
            val jsonElement = Json {
                ignoreUnknownKeys = true
                isLenient = true
            }.parseToJsonElement(result).jsonObject["results"]

            if (jsonElement != null) {
                println(jsonElement)
                val test2 = Json{
                    ignoreUnknownKeys = true
                    isLenient = true
                }.decodeFromJsonElement<List<Recipe>>(jsonElement)
                println(test2)
                recipeList = test2
                println(recipeList)
            }
        }

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