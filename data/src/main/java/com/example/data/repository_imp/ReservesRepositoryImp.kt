package com.example.data.repository_imp

import android.util.Log
import com.example.core.Either
import com.example.core.failure.Failure
import com.example.data.retrofit.ReservesRetrofit
import com.example.domain.repository.ReservesRepository
import com.example.domain.reserves.ReserveResponse

class ReservesRepositoryImp(private val reservesRetrofit: ReservesRetrofit): ReservesRepository {

    override suspend fun getUserReserves(userId: String): Either<Failure, List<ReserveResponse>?> {
        return try {
            val res = reservesRetrofit.getUserReserves(userId)
            when(res.isSuccessful){
                true -> Either.Sucess(res.body())
                false -> Either.Failure(Failure.ServerError)
            }
        }catch (e : Exception){
            Log.e("Error getUserReserves",e.message.toString())
            Either.Failure(Failure.Unknown)
        }
    }

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

    override suspend fun createNewReserve(deviceId: String): Either<Failure, String> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}