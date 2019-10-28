package com.example.data

import android.util.Log
import com.example.core.Either
import com.example.core.failure.Failure
import com.example.domain.User
import com.example.domain.sign_up.SignUpRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import kotlinx.coroutines.tasks.await
import java.lang.Exception

class SignUpRepositoryImp : SignUpRepository {

    private val auth by lazy {
        FirebaseAuth.getInstance()
    }

    override suspend fun signUp(user: User): Either<Failure, String> {
        return try {
            val task = auth.createUserWithEmailAndPassword(user.email, user.password).await()
            auth.currentUser?.sendEmailVerification()
            task.user?.let {
                Log.d("User UID",it.uid)
                Either.Sucess(it.uid)
            } ?: Either.Failure(Failure.Unknown)
        }catch (firebaseE : FirebaseAuthException){
            Log.e("Codigo Error Sign Up",firebaseE.errorCode)
            Either.Failure(Failure.Unknown)
        }catch (e : Exception){
            Log.i("Exception",e.message.toString())
            Either.Failure(Failure.Unknown)
        }
    }



}