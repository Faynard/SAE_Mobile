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
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.sae_mobile.Model.Recipe
import com.example.sae_mobile.Model.Recipes
import com.example.sae_mobile.network.ApiService
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.HttpTimeout
import kotlinx.coroutines.launch
import java.time.Duration
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

        /*-------------------------------------------------------Début affichage portion-------------------------------------------------------------------*/

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

        /*-------------------------------------------------------Fin affichage portion-------------------------------------------------------------------*/

        spinner = findViewById<Spinner>(R.id.dropdawn_genre)

        /*----------------------------------------------------------Début Spinner (Dropdown)--------------------------------------------------------------*/
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

        // Récupère la réponse du spinner choisi
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                // Récupérer l'élément sélectionné
                genreChoisi = parent?.getItemAtPosition(position).toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                return// Code à exécuter lorsque rien n'est sélectionné
            }
        }
        /*-----------------------------------------------------------------Fin Spinner (Dropdown) --------------------------------------------------------------*/

        /*----------------------------------------------------------Début Checkbox--------------------------------------------------------------*/
        val veganCheckBox : CheckBox = findViewById(R.id.vegan)
        val vegetarianCheckBox : CheckBox = findViewById(R.id.vegetarian)
        val glutenFreeCheckBox : CheckBox = findViewById(R.id.glutenfree)
        val dairyFreeCheckBox : CheckBox = findViewById(R.id.dairy_free)
        val primalCheckBox : CheckBox = findViewById(R.id.primal)
        veganCheckBox.isChecked

        /* Permet de filtrer en fonction des chekboxs et setup le string qui sera dans l'URL */
        button.setOnClickListener {
            var diets = ""
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
            /*----------------------------------------------------------Fin Checkbox--------------------------------------------------------------*/

            var listRecipes = Recipes()

            lifecycleScope.launch {
                /* Appel a l'api en remplissant l'URL avec les paramètres */
                listRecipes.recipes = apiService.fetchFilteredRecipes("2eb906031b4d49b8aa4e689d7cb79e0f",titleSearch.text.toString(),genreChoisi,portions.progress,diets)

                if (listRecipes.recipes.isNotEmpty()){
                    /* Si la réponse de l'api est pas vide on l'envoie dans l'activité ListRecette avec cette Intent() (envoi de la liste des objet) */
                    val intent = Intent(this@MainActivity, ListRecette::class.java)
                    intent.putExtra("liste", listRecipes)
                    startActivity(intent)
                } else {
                    /*Sinon un toast pour dire que cela n'éxiste pas */
                    Toast.makeText(this@MainActivity,"Aucune recette trouvée avec cette recherche", Toast.LENGTH_SHORT).show()
                }

            }

        }
    }
    /* Authorization Ktor */
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
