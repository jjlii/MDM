package com.example.domain.devices

import com.example.core.Either
import com.example.core.UseCase
import com.example.core.failure.Failure
import com.example.domain.DevicesRepository

class DevicesUseCase(private val devicesRepository: DevicesRepository) : UseCase<Failure,List<DevicesResponse>,Unit>() {

    override suspend fun run(params: Unit): Either<Failure, List<DevicesResponse>> = devicesRepository.getAllDevices()

}