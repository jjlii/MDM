package com.example.data.retrofit


import com.example.domain.devices.DevicesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface DevicesRetrofit {

    @GET("devices")
    suspend fun getAllDevices(): Response<List<DevicesResponse>>

    @GET("devices/{deviceId}")
    suspend fun getDeviceById(@Path("deviceId") deviceId : String) : Response<DevicesResponse>

}