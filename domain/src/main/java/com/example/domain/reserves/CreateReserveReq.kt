package com.example.domain.reserves

data class CreateReserveReq(
    val reserveResponse : ReserveResponse,
    val deviceId : String
)