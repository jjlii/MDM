package com.example.domain.sign_up

import com.example.core.Either
import com.example.core.UseCase
import com.example.core.failure.Failure
import com.example.domain.User

class SignUpUseCase(private val signUpRepository: SignUpRepository) : UseCase<Failure, String, User>() {

    override suspend fun run(params: User): Either<Failure, String> =
        signUpRepository.signUp(params)



}