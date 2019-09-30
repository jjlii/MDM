package com.example.mdm_everis.login

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.core.failure.Failure
import com.example.core.failure.UserFailure
import com.example.domain.User
import com.example.domain.login.LoginUseCase
import com.example.mdm_everis.base.BaseViewModel

class LoginViewModel(application: Application,
                     private val loginUseCase : LoginUseCase) : BaseViewModel(application){

    val loginLD = MutableLiveData<String>()
    val getLoginLD : LiveData<String> = loginLD



    fun login(email: String,password: String){
        loadingMLD.value=true
        loginUseCase(User(email, password)) { it.fold(::handleFailureLogin, ::handleSuccessLogin)}
    }



    private fun handleFailureLogin(failure: Failure){
        loadingMLD.value = false
        loginLD.value = when(failure){
            UserFailure.InvalidPassword -> "Contraseña incorrecta"
            UserFailure.InvalidEmailFormat -> "Formato del email incorrecto"
            UserFailure.InvalidEmail -> "No existe este usuario"
            Failure.NetworkConnection -> "Compruebe su conexión"
            else -> "Error desconocido"
        }
    }

    private fun handleSuccessLogin(user : String ){
        loadingMLD.value = false
        loginLD.value = user
    }
}