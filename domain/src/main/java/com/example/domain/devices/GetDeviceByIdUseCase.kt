package com.example.domain.devices

import com.example.core.Either
import com.example.core.UseCase
import com.example.core.failure.Failure
import com.example.domain.repository.DevicesRepository

class GetDeviceByIdUseCase(private val devicesRepository: DevicesRepository) :
    UseCase<Failure,DevicesResponse?,String> (){

    override suspend fun run(params: String): Either<Failure,DevicesResponse?> =
        devicesRepository.getDeviceById(params)

}