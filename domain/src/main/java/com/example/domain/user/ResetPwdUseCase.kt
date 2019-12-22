package com.example.domain.user

import com.example.core.Either
import com.example.core.UseCase
import com.example.core.failure.Failure
import com.example.domain.repository.UserRepository

class ResetPwdUseCase (private val userRepository: UserRepository) : UseCase<Failure,String,String>(){

    override suspend fun run(params: String): Either<Failure, String> =
        userRepository.resetPwd(params)

}