package com.example.mdm_everis.sign_up

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.core.failure.Failure
import com.example.domain.User
import com.example.domain.sign_up.SignUpUseCase
import com.example.mdm_everis.base.BaseViewModel

class SignUpViewModel(application: Application,
                      private val signUpUseCase: SignUpUseCase) : BaseViewModel(application) {

    //********************************** LiveData **************************************************

    private val signUpMLD = MutableLiveData<String>()
    val signUpLD : LiveData<String> = signUpMLD

    //********************************** End LiveData **********************************************

    //********************************** Sign up ***************************************************

    fun signUp(email : String, password : String){
        loadingMLD.value = true
        signUpUseCase(User(email,password)){it.fold(
            ::handleFailureSignUp,
            ::handleSuccessSignUp
        )
        }

    }

    private fun handleSuccessSignUp( user : String){
        loadingMLD.value = false
        signUpMLD.value = user
        Log.i("User",user)
    }

    private fun handleFailureSignUp( failure : Failure){
        loadingMLD.value = false
        signUpMLD.value = "Error"
        Log.i("failure", failure.toString())
    }

    //********************************** End Sign up ***********************************************

}