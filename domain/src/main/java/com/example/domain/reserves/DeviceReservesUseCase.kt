package com.example.domain.reserves

import com.example.core.Either
import com.example.core.UseCase
import com.example.core.failure.Failure
import com.example.domain.repository.DeviceReservesRepository

class DeviceReservesUseCase(private val deviceReservesRepository: DeviceReservesRepository) : UseCase<Failure,List<ReserveResponse>?,String>() {

    override suspend fun run(params: String): Either<Failure, List<ReserveResponse>?> =
        deviceReservesRepository.getDeviceReserves(params)

}