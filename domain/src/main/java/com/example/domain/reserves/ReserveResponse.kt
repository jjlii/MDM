package com.example.domain.reserves

data class ReserveResponse (
    val id : String,
    val userId : String,
    val deviceId : String,
    val startDate : Long,
    val endData : Long
)
