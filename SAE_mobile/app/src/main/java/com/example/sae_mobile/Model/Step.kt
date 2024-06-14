package com.example.sae_mobile.Model

import kotlinx.serialization.Serializable

@Serializable
data class Step(
    val number: Int,
    val step: String,
    val ingredients: List<Ingredient>,
    val equipment: List<Equipment>,
    val length: Length? = null

) {
}