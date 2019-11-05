package com.example.domain

import com.example.core.Either
import com.example.core.failure.Failure
import com.example.domain.user.UserResponse

interface UserRepository {
    suspend fun getUserById(userId : String) : Either<Failure, UserResponse?>
}