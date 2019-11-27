package com.example.domain.sessionManage

import com.example.core.Either
import com.example.core.UseCase
import com.example.core.failure.Failure
import com.example.domain.repository.SessionManageRepository

class SignOutUseCase(private val sessionManageRepository: SessionManageRepository): UseCase<Failure,Boolean,Unit>() {

    override suspend fun run(params: Unit): Either<Failure, Boolean> =
        sessionManageRepository.signOut()

}