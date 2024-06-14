package com.example.sae_mobile.Model

import kotlinx.serialization.Serializable

@Serializable
data class Length(
    val number: Int,
    val unit: String
)