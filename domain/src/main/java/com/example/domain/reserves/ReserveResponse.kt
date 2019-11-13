package com.example.domain.reserves



data class ReserveResponse (
    val id : String,
    val userId : String,
    val startDate : String,
    val endDate : String,
    val deviceId : String
)
