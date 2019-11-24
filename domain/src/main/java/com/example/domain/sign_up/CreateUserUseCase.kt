package com.example.domain.sign_up

import com.example.core.Either
import com.example.core.UseCase
import com.example.core.failure.Failure
import com.example.domain.repository.UserRepository
import com.example.domain.user.UserResponse

class CreateUserUseCase(private val userRepository: UserRepository) : UseCase<Failure,String,UserResponse>(){

    override suspend fun run(params: UserResponse): Either<Failure, String> =
        userRepository.createUser(params)

}