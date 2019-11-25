package com.example.domain.reserves

import com.example.core.Either
import com.example.core.UseCase
import com.example.core.failure.Failure
import com.example.domain.repository.ReservesRepository

class DeleteReserveUseCase(private val reservesRepository: ReservesRepository) : UseCase<Failure,ReserveResponse?,DeleteReserveReq>() {

    override suspend fun run(params: DeleteReserveReq): Either<Failure, ReserveResponse?> =
        reservesRepository.deleteReserve(params.deviceId,params.reserveId)

}