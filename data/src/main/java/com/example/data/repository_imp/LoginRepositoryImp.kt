package com.example.data.repository_imp

import android.util.Log
import com.example.core.Either
import com.example.core.failure.Failure
import com.example.core.failure.UserFailure
import com.example.domain.login.LoginRepository
import com.example.domain.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import kotlinx.coroutines.tasks.await
import java.lang.Exception

class LoginRepositoryImp : LoginRepository {
    private val auth by lazy {
        FirebaseAuth.getInstance()
    }


    override suspend fun login(user: User): Either<Failure, String> {
        return try {
            val task = auth.signInWithEmailAndPassword(user.email, user.password).await()
            
            task.user?.let {
                if(it.isEmailVerified){
                    Log.d("User UID",it.uid)
                    Either.Sucess(it.uid)
                }else{
                    Either.Failure(UserFailure.EmailNoVerified)
                }
            } ?: Either.Failure(Failure.Unknown)
        }catch (firebaseE : FirebaseAuthException){
            when(firebaseE.errorCode){
                "ERROR_WRONG_PASSWORD" -> Either.Failure(UserFailure.InvalidPassword)
                "ERROR_INVALID_EMAIL"-> Either.Failure(UserFailure.InvalidEmailFormat)
                "ERROR_USER_NOT_FOUND" -> Either.Failure(UserFailure.InvalidEmail)
                else -> Either.Failure(Failure.Unknown)
            }
        }catch (e : Exception){
            Log.i("Exception",e.message.toString())
            Either.Failure(Failure.Unknown)
        }
    }

}