package com.example.data.retrofit

import com.example.domain.reserves.ReserveResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ReservesRetrofit {

    @GET("reserves/user/{userId}")
    suspend fun getUserReserves(@Path("userId") userId : String) : Response<List<ReserveResponse>>

    @GET("reserves/device/{deviceId}")
    suspend fun getDeviceReserves(@Path("deviceId")deviceId : String) : Response<List<ReserveResponse>>

    @POST("reserves/device/{deviceId}")
    suspend fun postDeviceReserves(@Body reserve : ReserveResponse, @Path("deviceId")deviceId: String ) : Response<String>

}