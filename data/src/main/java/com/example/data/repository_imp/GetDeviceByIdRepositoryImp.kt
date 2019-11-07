package com.example.data.repository_imp

import android.util.Log
import com.example.core.Either
import com.example.core.failure.Failure
import com.example.data.retrofit.DevicesRetrofit
import com.example.domain.repository.GetDeviceByIdRepository
import com.example.domain.devices.DevicesResponse

class GetDeviceByIdRepositoryImp(private val devicesRetrofit: DevicesRetrofit) :
    GetDeviceByIdRepository {

    override suspend fun getDeviceById(deviceId: String): Either<Failure, DevicesResponse?> {
        return try {
            val res = devicesRetrofit.getDeviceById(deviceId)
            when(res.isSuccessful){
                true -> Either.Sucess(res.body())
                false -> Either.Failure(Failure.ServerError)
            }
        }catch (e : Exception){
            Log.e("Error getDeviceById",e.message.toString())
            Either.Failure(Failure.Unknown)
        }
    }

}