package com.example.data.retrofit

import com.example.domain.reserves.CaducatedReserveResponse
import com.example.domain.reserves.ReserveResponse
import retrofit2.Response
import retrofit2.http.*

interface ReservesRetrofit {

    @GET("users/{userId}/reserves")
    suspend fun getUserReserves(@Path("userId") userId : String)
            : Response<List<ReserveResponse>>

    @GET("devices/{deviceId}/reserves")
    suspend fun getDeviceReserves(@Path("deviceId")deviceId : String)
            : Response<List<ReserveResponse>>

    @POST("devices/{deviceId}/reserves")
    suspend fun postDeviceReserves(@Body reserve : ReserveResponse, @Path("deviceId")deviceId: String )
            : Response<ReserveResponse>

    @DELETE("devices/{deviceId}/reserves/{reserveId}")
    suspend fun deleteDeviceReserve(@Path("deviceId") deviceId : String, @Path("reserveId") reserveId: String)
            : Response<ReserveResponse>

    @POST("caducatedReserves")
    suspend fun createCaducatedReserve(@Body reserve : ReserveResponse)
            : Response<CaducatedReserveResponse>

}