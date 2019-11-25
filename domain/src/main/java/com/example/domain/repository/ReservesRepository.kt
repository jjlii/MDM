package com.example.domain.repository

import com.example.core.Either
import com.example.core.failure.Failure
import com.example.domain.reserves.ReserveResponse

interface ReservesRepository {

    suspend fun getUserReserves(userId : String) : Either<Failure, List<ReserveResponse>?>

    suspend fun getDeviceReserves(deviceId : String) : Either<Failure,List<ReserveResponse>?>

    suspend fun createNewReserve(reserve : ReserveResponse ,deviceId : String) : Either<Failure,ReserveResponse?>

    suspend fun deleteReserve(deviceId: String, reserveId : String) : Either<Failure,ReserveResponse?>

    suspend fun createCaducatedReserve(reserve : ReserveResponse) : Either<Failure, String?>

}