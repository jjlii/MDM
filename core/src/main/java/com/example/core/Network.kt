package com.example.core

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Network {

    fun initRetrofit(): Retrofit = Retrofit.Builder()
        .baseUrl("")
        .client(initOkHttpClient())
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private fun initOkHttpClient() = OkHttpClient.Builder().build()

}