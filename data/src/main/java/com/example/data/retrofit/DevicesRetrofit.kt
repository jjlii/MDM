package com.example.data.retrofit


import com.example.domain.devices.DevicesResponse
import retrofit2.Response
import retrofit2.http.GET

interface DevicesRetrofit {

    @GET("/getAllDevices")
    suspend fun getAllDevices(): Response<List<DevicesResponse>>

}