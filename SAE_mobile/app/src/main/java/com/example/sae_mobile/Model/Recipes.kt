package com.example.sae_mobile.Model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class Recipes( var recipes: List<Recipe> = mutableListOf() ):Parcelable {

}