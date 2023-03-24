package com.dhana.bandmate.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class BandMember(
    val name: String,
    val position: String,
) : Parcelable
