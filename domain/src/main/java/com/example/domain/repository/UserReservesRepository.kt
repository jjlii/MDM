package com.example.domain.repository

import com.example.core.Either
import com.example.core.failure.Failure
import com.example.domain.reserves.ReserveResponse

interface UserReservesRepository {
    suspend fun getUserReserves(userId : String) : Either<Failure,List<ReserveResponse>?>
}