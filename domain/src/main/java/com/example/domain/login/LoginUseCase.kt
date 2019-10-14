package com.example.domain.login

import com.example.core.Either
import com.example.core.UseCase
import com.example.core.failure.Failure
import com.example.domain.User

class LoginUseCase(private val loginRepository: LoginRepository) : UseCase<Failure, String, User>(){


    override suspend fun run(params: User): Either<Failure, String> = loginRepository.
        login(params)

}