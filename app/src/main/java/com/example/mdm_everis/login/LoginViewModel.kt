package com.example.mdm_everis.login

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.core.Constant
import com.example.core.failure.Failure
import com.example.core.failure.UserFailure
import com.example.domain.devices.DevicesUseCase
import com.example.domain.login.LoginUseCase
import com.example.domain.reserves.UserReservesUseCase
import com.example.domain.user.GetUserByIdUserCase
import com.example.domain.user.User
import com.example.mdm_everis.base.BaseViewModel

class LoginViewModel(application: Application,
                     getUserByIdUserCase: GetUserByIdUserCase,
                     devicesUseCase: DevicesUseCase,
                     userReservesUseCase: UserReservesUseCase,
                     private val loginUseCase : LoginUseCase
) : BaseViewModel(application,getUserByIdUserCase,devicesUseCase,userReservesUseCase){

    //********************************** LiveData **************************************************


    private val loginMLD = MutableLiveData<String>()
    val loginLD : LiveData<String> = loginMLD

    //********************************** End LiveData **********************************************

    //********************************** Login *****************************************************


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
            UserFailure.EmailNoVerified -> Constant.ErrorLogin.EMAIL_NO_VERIFIED
            Failure.NetworkConnection -> Constant.ErrorLogin.ERROR_CONEXION
            else -> Constant.ErrorGeneral.ERROR_DESCONOCIDO
        }
    }

    private fun handleSuccessLogin(user : String ){
        loginMLD.value = user
    }



}