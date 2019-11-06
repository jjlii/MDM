package com.example.mdm_everis

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Favorites (val fav : List<String>) : Parcelable