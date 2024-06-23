package com.example.sae_mobile

import android.os.Build
import android.os.Bundle
import android.text.Html
import android.text.method.ScrollingMovementMethod
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import com.example.sae_mobile.Model.Recipe
import com.squareup.picasso.Picasso


class RecetteChoisie : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.recette_choix)

        /* Récupération de la recette */
        val recetteChoisi = intent.getParcelableExtra("recipe", Recipe::class.java)

        if (recetteChoisi == null) {
            finish()
        }



        val cancelButton : ImageButton = findViewById(R.id.cancelButton)
        val title : TextView = findViewById(R.id.nomRecette)
        val portions : TextView = findViewById(R.id.nbPortion)
        val tempsPreparation : TextView = findViewById(R.id.tempPreparation)
        val imageRecette : ImageView = findViewById(R.id.imageRecette)
        val description : TextView = findViewById(R.id.description)
        val go : Button = findViewById(R.id.id_btn_go)
        val fragmentContainer: View = findViewById(R.id.fragment)


        cancelButton.setOnClickListener {
            finish()
        }

        /*-------------------------------------------Début affichage de l'object recette-------------------------------------------------------*/

        description.text = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Html.fromHtml(recetteChoisi!!.summary, Html.FROM_HTML_MODE_COMPACT)
        } else {
            Html.fromHtml(recetteChoisi!!.summary)
        }
        description.movementMethod = ScrollingMovementMethod()

        Picasso.get()
            .load(recetteChoisi!!.image)
            .into(imageRecette)

        if (recetteChoisi!!.preparationMinutes == null){
            tempsPreparation.text = "Non renseignée"
        } else {
            tempsPreparation.text = recetteChoisi!!.preparationMinutes.toString()
        }
        title.text = recetteChoisi!!.title
        portions.text = recetteChoisi!!.servings.toString()

        /* Activation du fragment */
        go.setOnClickListener {

            val instruction = recetteChoisi!!.analyzedInstructions[0]
            var instructionString = ""
            /* Boucle pour préparer les étapes les unes à la suite des autres */
            instruction.steps.forEach { step ->
                if (step.length == null) {
                    instructionString += "Etape : ${step.number}  \n" +
                            "Durée : Non renseignée\n" +
                            "${step.step}\n\n"
                } else{
                    instructionString += "Etape : ${step.number}  \n" +
                            "Durée : ${step.length!!.number} minutes\n" +
                            "${step.step}\n\n"
                }
            }

            /* Envoi de ces étapes et activation du fragment */
            val fragment = FragmentRecette.newInstance(instructionString)
            val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
            transaction.add(R.id.fragment, fragment)
            transaction.addToBackStack(null)
            transaction.commit()
            fragmentContainer.visibility = View.VISIBLE
        }
        /*-------------------------------------------Fin affichage de l'object recette-------------------------------------------------------*/

    }
}