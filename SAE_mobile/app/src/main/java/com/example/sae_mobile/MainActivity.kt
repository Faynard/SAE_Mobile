package com.example.sae_mobile


import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.CheckBox
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.sae_mobile.Model.Recipe
import com.example.sae_mobile.Model.Recipes
import com.example.sae_mobile.network.ApiService
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.HttpTimeout
import kotlinx.coroutines.launch
import java.util.ArrayList

class MainActivity : AppCompatActivity() {

    lateinit var spinner: Spinner
    var apiService : ApiService = ApiService(kTorClient)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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

        /*----------------------------------------------------------Début Spinner--------------------------------------------------------------*/
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
        /*-----------------------------------------------------------------Fin Spinner--------------------------------------------------------------*/

        /**
         * Checkbox
         */
        val veganCheckBox : CheckBox = findViewById(R.id.vegan)
        val vegetarianCheckBox : CheckBox = findViewById(R.id.vegetarian)
        val glutenFreeCheckBox : CheckBox = findViewById(R.id.glutenfree)
        val dairyFreeCheckBox : CheckBox = findViewById(R.id.dairy_free)
        val primalCheckBox : CheckBox = findViewById(R.id.primal)
        veganCheckBox.isChecked

        button.setOnClickListener {
            var diets : String = ""
            if (veganCheckBox.isChecked){
                diets += "vegan,"
            }
            if (vegetarianCheckBox.isChecked){
                diets += "vegetarian,"
            }
            if (glutenFreeCheckBox.isChecked){
                diets += "gluten free,"
            }
            if (dairyFreeCheckBox.isChecked){
                diets += "dairy free,"
            }
            if (primalCheckBox.isChecked){
                diets += "primal,"
            }

            if (diets.length > 1){
                diets.substring(0, diets.length - 1)
            }

            println("Paramètres : ${titleSearch.text.toString()}, ${genreChoisi}, ${portions.progress}")
            var listRecipes: Recipes = Recipes()
            lifecycleScope.launch {
                listRecipes.recipes = apiService.fetchFilteredRecipes("2eb906031b4d49b8aa4e689d7cb79e0f",titleSearch.text.toString(),genreChoisi,portions.progress,diets)
                val intent = Intent(this@MainActivity, ListRecette::class.java)
                intent.putExtra("liste", listRecipes)
                startActivity(intent)
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
