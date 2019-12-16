package com.example.data.repository_imp

import android.util.Log
import com.example.core.Either
import com.example.core.failure.Failure
import com.example.core.failure.UserFailure
import com.example.domain.repository.SessionManageRepository
import com.example.domain.sessionManage.LoginResponse
import com.example.domain.user.User
import com.google.firebase.auth.*
import com.google.firebase.iid.FirebaseInstanceId
import kotlinx.coroutines.tasks.await
import java.lang.Exception

class SessionManageRepositoryImp : SessionManageRepository {
    
    private val auth by lazy {
        FirebaseAuth.getInstance()
    }


    override suspend fun login(user: User): Either<Failure, LoginResponse> {
        return try {
            val task = auth.signInWithEmailAndPassword(user.email, user.password).await()
            val instanceIdResult = FirebaseInstanceId.getInstance().instanceId.await()
            Log.d("TOKEN", instanceIdResult.token)
            task.user?.let {
                if(it.isEmailVerified){
                    Log.d("User UID",it.uid)
                    Either.Sucess(LoginResponse(it.uid,instanceIdResult.token))
                }else{
                    Either.Failure(UserFailure.EmailNoVerified)
                }
            } ?: Either.Failure(Failure.Unknown)
        }catch (firebaseE : FirebaseAuthException){
            when(firebaseE.errorCode){
                "ERROR_WRONG_PASSWORD" -> Either.Failure(UserFailure.InvalidPassword)
                "ERROR_INVALID_EMAIL"-> Either.Failure(UserFailure.InvalidEmailFormat)
                "ERROR_USER_NOT_FOUND" -> Either.Failure(UserFailure.InvalidEmail)
                else -> Either.Failure(Failure.NetworkConnection)
            }
        }catch (e : Exception){
            Log.i("Exception",e.message.toString())
            Either.Failure(Failure.Unknown)
        }
    }

    override suspend fun signOut(): Either<Failure, Boolean> {
        return try {
            auth.signOut()
            Either.Sucess(true)
        }catch (e : Exception){
            Log.i("Exception",e.message.toString())
            Either.Failure(Failure.Unknown)
        }
    }

}