package com.example.domain.login

import com.example.core.Either
import com.example.core.failure.Failure
import com.example.domain.user.User


interface LoginRepository {

    suspend fun login(user: User): Either<Failure,String>
}