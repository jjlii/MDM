package com.example.data.repository_imp

import android.util.Log
import com.example.core.Either
import com.example.core.failure.Failure
import com.example.data.retrofit.DevicesRetrofit
import com.example.domain.repository.DevicesRepository
import com.example.domain.devices.DevicesResponse
import java.lang.Exception

class DevicesRepositoryImp(private val devicesRetrofit : DevicesRetrofit) :
    DevicesRepository {
    override suspend fun getAllDevices(): Either<Failure, List<DevicesResponse>?> {
        return try {
            val resp = devicesRetrofit.getAllDevices()
            when (resp.isSuccessful){
                true -> Either.Sucess(resp.body())
                false -> Either.Failure(Failure.ServerError)
            }
        }catch (e : Exception){
            Log.e("Error getAllDevices",e.message.toString())
            Either.Failure(Failure.Unknown)
        }
    }

}