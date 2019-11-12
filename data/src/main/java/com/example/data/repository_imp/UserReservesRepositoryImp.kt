package com.example.data.repository_imp

import android.util.Log
import com.example.core.Either
import com.example.core.failure.Failure
import com.example.data.retrofit.ReservesRetrofit
import com.example.domain.repository.UserReservesRepository
import com.example.domain.reserves.ReserveResponse

class UserReservesRepositoryImp(private val reservesRetrofit: ReservesRetrofit) : UserReservesRepository {

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


}