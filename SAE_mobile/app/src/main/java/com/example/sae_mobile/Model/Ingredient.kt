package com.example.sae_mobile.Model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
data class Ingredient(
    val id: Int,
    val name: String,
    val localizedName: String,
    val image: String
): Parcelable