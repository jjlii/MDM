package com.example.mdm_everis.login

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.core.failure.Failure
import com.example.core.failure.UserFailure
import com.example.mdm_everis.base.BaseViewModel
import com.google.firebase.auth.FirebaseUser

class LoginViewModel(application: Application) : BaseViewModel(application){

    val loginLD = MutableLiveData<String>()
    val getLoginLD : LiveData<String> = loginLD

    private fun handleFailureLogin(failure: Failure){
        loginLD.value = when(failure){
            UserFailure.InvalidPassword -> "Contraseña incorrecta"
            UserFailure.InvalidEmailFormat -> "Formato del email incorrecto"
            UserFailure.InvalidEmail -> "No existe este usuario"
            Failure.NetworkConnection -> "Compruebe su conexión"
            else -> "Error desconocido"
        }
    }

    private fun handleSuccessLogin(firebaseUser: FirebaseUser){
        loginLD.value = "Success"
    }
}