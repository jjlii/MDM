package com.example.mdm_everis.login

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.core.Constant
import com.example.core.failure.Failure
import com.example.core.failure.UserFailure
import com.example.domain.User
import com.example.domain.login.LoginUseCase
import com.example.mdm_everis.base.BaseViewModel

class LoginViewModel(application: Application,
                     private val loginUseCase : LoginUseCase) : BaseViewModel(application){

    private val loginMLD = MutableLiveData<String>()
    val loginLD : LiveData<String> = loginMLD



    fun login(email: String,password: String){
        loadingMLD.value=true
        loginUseCase(User(email, password)) { it.fold(
            ::handleFailureLogin,
            ::handleSuccessLogin)
        }
    }



    private fun handleFailureLogin(failure: Failure){
        loadingMLD.value = false
        loginMLD.value = when(failure){
            UserFailure.InvalidPassword -> Constant.ErrorLogin.CONTRESENIA_INCORRECTA
            UserFailure.InvalidEmailFormat -> Constant.ErrorLogin.FORMATO_EMAIL_INCORRECTO
            UserFailure.InvalidEmail -> Constant.ErrorLogin.NO_EXISTE_USUARIO
            Failure.NetworkConnection -> Constant.ErrorLogin.ERROR_CONEXION
            else -> Constant.ErrorGeneral.ERROR_DESCONOCIDO
        }
    }

    private fun handleSuccessLogin(user : String ){
        loadingMLD.value = false
        loginMLD.value = user
    }
}