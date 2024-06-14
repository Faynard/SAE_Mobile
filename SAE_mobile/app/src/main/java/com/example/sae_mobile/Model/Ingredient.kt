package com.example.sae_mobile.Model

import kotlinx.serialization.Serializable

@Serializable
data class Ingredient(
    val id: Int,
    val name: String,
    val localizedName: String,
    val image: String
)