package com.example.domain.user

import com.example.core.Either
import com.example.core.UseCase
import com.example.core.failure.Failure
import com.example.domain.UserRepository

class GetUserByIdUserCase(private val userRepository: UserRepository) : UseCase<Failure,UserResponse?,String>() {

    override suspend fun run(params: String): Either<Failure, UserResponse?> =
        userRepository.getUserById(params)

}