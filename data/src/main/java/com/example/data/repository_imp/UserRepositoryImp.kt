package com.example.data.repository_imp

import android.util.Log
import com.example.core.Either
import com.example.core.failure.Failure
import com.example.data.retrofit.UserRetrofit
import com.example.domain.repository.UserRepository
import com.example.domain.user.UserResponse

class UserRepositoryImp(private val userRetrofit: UserRetrofit) :
    UserRepository {

    override suspend fun getUserById(userId: String): Either<Failure, UserResponse?> {
        return try {
            val resp = userRetrofit.getUserById(userId)
            when(resp.isSuccessful){
                true -> Either.Sucess(resp.body())
                false -> Either.Failure(Failure.ServerError)
            }
        }catch (e : Exception){
            Log.e("Error getAllDevices",e.message.toString())
            Either.Failure(Failure.Unknown)
        }
    }

    override suspend fun createUser(user: UserResponse): Either<Failure, String> {
        return try {
            val resp = userRetrofit.createUser(user)
            when(resp.isSuccessful){
                true ->Either.Sucess("User created")
                false -> Either.Failure(Failure.ServerError)
            }
        }catch (e : Exception){
            Log.e("Error getAllDevices",e.message.toString())
            Either.Failure(Failure.Unknown)
        }
    }

}