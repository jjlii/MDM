package com.example.data

import android.util.Log
import com.example.core.Either
import com.example.core.failure.Failure
import com.example.data.retrofit.DevicesRetrofit
import com.example.domain.GetDeviceByIdRepository
import com.example.domain.devices.DevicesResponse

class GetDeviceByIdRepositoryImp(private val devicesRetrofit: DevicesRetrofit) : GetDeviceByIdRepository {

    override suspend fun getDeviceById(deviceId: String): Either<Failure, DevicesResponse?> {
        try {

        }catch (e : Exception){
            Log.e("Error getDeviceById",e.message.toString())
            Either.Failure(Failure.Unknown)
        }
    }

}