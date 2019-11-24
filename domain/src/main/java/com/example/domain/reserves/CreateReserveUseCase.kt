package com.example.domain.reserves

import com.example.core.Either
import com.example.core.UseCase
import com.example.core.failure.Failure
import com.example.domain.repository.ReservesRepository

class CreateReserveUseCase(private val reservesRepository: ReservesRepository): UseCase<Failure,ReserveResponse?,CreateReserveReq>() {

    override suspend fun run(params: CreateReserveReq): Either<Failure, ReserveResponse?> =
        reservesRepository.createNewReserve(params.reserveResponse,params.deviceId)

}