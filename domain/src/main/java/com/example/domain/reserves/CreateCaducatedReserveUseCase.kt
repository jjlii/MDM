package com.example.domain.reserves

import com.example.core.Either
import com.example.core.UseCase
import com.example.core.failure.Failure
import com.example.domain.repository.ReservesRepository

class CreateCaducatedReserveUseCase (private val reservesRepository: ReservesRepository) : UseCase<Failure,String?,ReserveResponse>(){

    override suspend fun run(params: ReserveResponse): Either<Failure, String?> =
        reservesRepository.createCaducatedReserve(params)

}