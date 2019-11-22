package com.example.mdm_everis.parcelable_data

import android.os.Parcelable
import com.example.domain.reserves.ReserveResponse
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Reserves(
    var allReserves : List<ReserveResponse>
): Parcelable