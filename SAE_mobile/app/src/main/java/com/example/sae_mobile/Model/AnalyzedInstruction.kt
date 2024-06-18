package com.example.sae_mobile.Model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
data class AnalyzedInstruction(
    val name: String,
    val steps: List<Step>
): Parcelable