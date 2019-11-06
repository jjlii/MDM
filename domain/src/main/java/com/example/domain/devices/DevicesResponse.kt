package com.example.domain.devices

import com.sun.xml.internal.ws.developer.Serialization
import java.io.Serializable


data class DevicesResponse (
    val id : String,
    val brand : String,
    val isMobile : Boolean,
    val model : String,
    val ppi : String,
    val picture : String,
    val sim : Boolean,
    val so : String,
    val screenResolution : String,
    val screenSize : String,
    val typeCharger : String,
    val version : String
) : Serializable