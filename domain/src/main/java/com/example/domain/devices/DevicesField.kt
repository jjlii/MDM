package com.example.domain.devices

import com.google.gson.annotations.SerializedName

data class DevicesField(
                        @field:SerializedName("Brand") val brand:String,
                        @field:SerializedName("Model") val model:String,
                        @field:SerializedName("SO") val so:String,
                        @field:SerializedName("Version") val version : String,
                        @field:SerializedName("IsMobile") val isMobile: Boolean,
                        @field:SerializedName("ScreenResolution") val screenResolution : String,
                        @field:SerializedName("PPI") val ppi : String,
                        @field:SerializedName("ScreenSize") val screenSize : String,
                        @field:SerializedName("SIM") val sim : Boolean,
                        @field:SerializedName("TypeCharger")val typeCharger : String,
                        @field:SerializedName("Picture") val picture : String
)
