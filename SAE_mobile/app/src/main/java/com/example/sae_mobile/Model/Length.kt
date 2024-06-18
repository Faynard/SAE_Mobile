package com.example.sae_mobile.Model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
data class Length(
    val number: Int,
    val unit: String
): Parcelable