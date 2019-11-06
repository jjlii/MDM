package com.example.mdm_everis.login

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.core.Constant
import com.example.core.failure.Failure
import com.example.core.failure.UserFailure
import com.example.domain.User
import com.example.domain.devices.DevicesResponse
import com.example.domain.devices.DevicesUseCase
import com.example.domain.login.LoginUseCase
import com.example.domain.user.GetUserByIdUserCase
import com.example.domain.user.UserResponse
import com.example.mdm_everis.base.BaseViewModel

class LoginViewModel(application: Application,
                     private val loginUseCase : LoginUseCase,
                     private val getUserByIdUserCase: GetUserByIdUserCase,
                     private val devicesUseCase: DevicesUseCase) : BaseViewModel(application){

    //********************************** LiveData **************************************************

    private val loginMLD = MutableLiveData<String>()
    val loginLD : LiveData<String> = loginMLD

    private val getUserByIdMLD = MutableLiveData<UserResponse>()
    val getUserByIdLD : LiveData<UserResponse> = getUserByIdMLD

    private val devicesMLD = MutableLiveData<List<DevicesResponse>>()
    val devicesLD : LiveData<List<DevicesResponse>> = devicesMLD

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
        loadingMLD.value = false
        loginMLD.value = user
    }

    //********************************** End Login *************************************************

    //********************************** Get User **************************************************

    fun getUserById( userId : String ){
        loadingMLD.value = true
        getUserByIdUserCase(userId){
            it.fold(
                ::handleFailGetUserById,
                ::handleSuccessGetUserById
            )
        }
    }

    private fun handleSuccessGetUserById(userResponse: UserResponse?){
        loadingMLD.value = false
        getUserByIdMLD.value = userResponse
    }

    private fun handleFailGetUserById(failure: Failure){
        loadingMLD.value = false
        getUserByIdMLD.value = null
    }

    //********************************** End Get User **********************************************

    //********************************** Get Devices ***********************************************

    fun allDevies(){
        loadingMLD.value = true
        devicesUseCase(Unit){
            it.fold(
                ::handleFailureAllDevices,
                ::handleSuccessAllDevices
            )
        }
    }

    private fun handleFailureAllDevices(failure: Failure){
        loadingMLD.value = false
        devicesMLD.value = null
    }

    private fun handleSuccessAllDevices(allDevices : List<DevicesResponse>?){
        loadingMLD.value = false
        devicesMLD.value = allDevices
    }

    //********************************** End Get Devices *******************************************

}