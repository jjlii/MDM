package com.example.data.retrofit

import com.example.domain.login.UserResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface UserRetrofit {

    @GET("/users/{userId}")
    suspend fun getUserById(@Path("userId") userId : String) : Response<UserResponse>

}