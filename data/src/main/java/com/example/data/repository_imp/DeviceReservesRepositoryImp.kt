package com.example.data.repository_imp

import android.util.Log
import com.example.core.Either
import com.example.core.failure.Failure
import com.example.data.retrofit.ReservesRetrofit
import com.example.domain.repository.DeviceReservesRepository
import com.example.domain.reserves.ReserveResponse

class DeviceReservesRepositoryImp(private val reservesRetrofit: ReservesRetrofit) : DeviceReservesRepository{

    override suspend fun getDeviceReserves(deviceId: String): Either<Failure, List<ReserveResponse>?> {
        return try {
            val res = reservesRetrofit.getDeviceReserves(deviceId)
            when(res.isSuccessful){
                true -> Either.Sucess(res.body())
                false -> Either.Failure(Failure.ServerError)
            }
        }catch (e : Exception){
            Log.e("Error getUserReserves",e.message.toString())
            Either.Failure(Failure.Unknown)
        }
    }

}