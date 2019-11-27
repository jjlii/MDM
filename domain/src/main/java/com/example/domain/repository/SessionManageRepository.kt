package com.example.domain.repository

import com.example.core.Either
import com.example.core.failure.Failure
import com.example.domain.user.User


interface SessionManageRepository {

    suspend fun login(user: User): Either<Failure,String>

    suspend fun signOut() : Either<Failure,Boolean>

}