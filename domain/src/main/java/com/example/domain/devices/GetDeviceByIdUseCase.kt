package com.example.domain.devices

import com.example.core.Either
import com.example.core.UseCase
import com.example.core.failure.Failure
import com.example.domain.GetDeviceByIdRepository

class GetDeviceByIdUseCase(private val getDeviceByIdRepository: GetDeviceByIdRepository) :
    UseCase<Failure,DevicesResponse?,String> (){

    override suspend fun run(params: String): Either<Failure,DevicesResponse?> =
        getDeviceByIdRepository.getDeviceById(params)

}