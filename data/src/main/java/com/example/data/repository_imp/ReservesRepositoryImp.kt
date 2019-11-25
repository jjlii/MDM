package com.example.data.repository_imp

import android.util.Log
import com.example.core.Either
import com.example.core.failure.Failure
import com.example.core.failure.ReserveFailure
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

    override suspend fun createNewReserve(reserve: ReserveResponse, deviceId: String): Either<Failure, ReserveResponse?> {
        return try {
            val res = reservesRetrofit.postDeviceReserves(reserve,deviceId)
            when(res.code()){
                200-> Either.Sucess(res.body())
                400-> Either.Failure(ReserveFailure.InvalidReserve)
                else -> Either.Failure(Failure.ServerError)
            }
        }catch (e : Exception){
            Log.e("Error createNewReserve",e.message.toString())
            Either.Failure(Failure.Unknown)
        }
    }

    override suspend fun deleteReserve(deviceId: String, reserveId: String): Either<Failure, ReserveResponse?> {
        return try {
            val res = reservesRetrofit.deleteDeviceReserve(deviceId,reserveId)
            when(res.isSuccessful){
                true -> Either.Sucess(res.body())
                false -> Either.Failure(Failure.ServerError)
            }
        }catch (e : Exception){
            Log.e("Error createNewReserve",e.message.toString())
            Either.Failure(Failure.Unknown)
        }
    }

    override suspend fun createCaducatedReserve(reserve: ReserveResponse): Either<Failure, String?> {
        return try {
            val res = reservesRetrofit.createCaducatedReserve(reserve)
            when(res.isSuccessful){
                true -> Either.Sucess("Success")
                false -> Either.Failure(Failure.ServerError)
            }
        }catch (e : Exception){
            Log.e("Error caducatedReserve",e.message.toString())
            Either.Failure(Failure.Unknown)
        }
    }

}