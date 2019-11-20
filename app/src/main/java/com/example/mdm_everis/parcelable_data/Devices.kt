package com.example.mdm_everis.parcelable_data

import android.os.Parcelable
import com.example.domain.devices.DevicesResponse
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Devices (
    var allDevices : List<DevicesResponse>) : Parcelable