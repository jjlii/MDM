package com.example.data.retrofit

import com.example.domain.reserves.ReserveResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ReservesRetrofit {

    @GET("reserves/user/{userId}")
    suspend fun getUserReserves(@Path("userId") userId : String) : Response<ReserveResponse>

}