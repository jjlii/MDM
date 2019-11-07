package com.example.domain.repository

import com.example.core.Either
import com.example.core.failure.Failure
import com.example.domain.devices.DevicesResponse

interface GetDeviceByIdRepository {

    suspend fun getDeviceById(deviceId : String) : Either<Failure,DevicesResponse?>

}