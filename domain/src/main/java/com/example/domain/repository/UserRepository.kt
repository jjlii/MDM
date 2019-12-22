package com.example.domain.repository

import com.example.core.Either
import com.example.core.failure.Failure
import com.example.domain.user.User
import com.example.domain.user.UserResponse

interface UserRepository {

    suspend fun getUserById(userId : String) : Either<Failure, UserResponse?>

    suspend fun createUser(user : UserResponse) : Either<Failure,String>

    suspend fun resetPwd(email : String) : Either<Failure,String>

}