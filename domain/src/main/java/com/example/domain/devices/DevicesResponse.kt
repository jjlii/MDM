package com.example.domain.devices

import com.google.gson.annotations.SerializedName

data class DevicesResponse (

    @field:SerializedName("documents")
    val devices: List<DevicesItems>
)