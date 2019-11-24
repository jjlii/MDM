package com.example.data.retrofit

import com.example.domain.user.UserResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface UserRetrofit {

    @GET("/users/{userId}")
    suspend fun getUserById(@Path("userId") userId : String) : Response<UserResponse>

    @POST("/users")
    suspend fun createUser(@Body user : UserResponse) : Response<UserResponse>

}