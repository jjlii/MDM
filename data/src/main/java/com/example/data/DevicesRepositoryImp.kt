package com.example.data

import com.example.core.Either
import com.example.core.failure.Failure
import com.example.data.retrofit.DevicesRetrofit
import com.example.domain.devices.DevicesField
import com.example.domain.DevicesRepository
import java.lang.Exception

class DevicesRepositoryImp(private val devicesRetrofit : DevicesRetrofit) : DevicesRepository {
    override suspend fun getAllDevices(): Either<Failure, List<DevicesField>> {
        return try {
            val resp = devicesRetrofit.getAllDevices()
            when (resp.isSuccessful){
                true -> Either.Sucess(resp.body()!!)
                false -> Either.Failure(Failure.ServerError)
            }
        }catch (e : Exception){
            Either.Failure(Failure.Unknown)
        }
    }
}