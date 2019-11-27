package com.example.domain.sessionManage

import com.example.core.Either
import com.example.core.UseCase
import com.example.core.failure.Failure
import com.example.domain.repository.SessionManageRepository
import com.example.domain.user.User

class LoginUseCase(private val sessionManageRepository: SessionManageRepository) : UseCase<Failure, String, User>(){


    override suspend fun run(params: User): Either<Failure, String> = sessionManageRepository.
        login(params)

}