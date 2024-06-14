package com.example.sae_mobile.Model

import kotlinx.serialization.Serializable

@Serializable
data class AnalyzedInstruction(
    val name: String,
    val steps: List<Step>
)