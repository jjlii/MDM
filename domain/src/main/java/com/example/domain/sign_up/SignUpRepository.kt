package com.example.domain.sign_up

import com.example.core.Either
import com.example.core.failure.Failure
import com.example.domain.User

interface SignUpRepository{

    suspend fun signUp(user: User) : Either<Failure, String>

}