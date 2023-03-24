package com.dhana.bandmate.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Band(
    val id: Int,
    val name: String,
    val description: String,
    val photo: Int
) : Parcelable
