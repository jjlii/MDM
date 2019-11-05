package com.example.domain

import com.example.core.Either
import com.example.core.failure.Failure
import com.example.domain.devices.DevicesResponse

interface DevicesRepository {

    suspend fun getAllDevices() : Either<Failure,List<DevicesResponse>?>
}