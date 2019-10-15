package com.example.domain.devices

import com.google.gson.annotations.SerializedName

data class DevicesItems(

    val createTime: String,


    val name: String,


    val updateTime: String,


    val fields: DevicesField
)