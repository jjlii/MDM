package com.example.data.retrofit

import com.example.domain.devices.DevicesField
import retrofit2.Response
import retrofit2.http.GET

interface DevicesRetrofit {

    @GET("Devices")
    suspend fun getAllDevices(): Response<List<DevicesField>>

}