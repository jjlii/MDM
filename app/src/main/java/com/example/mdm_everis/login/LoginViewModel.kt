package com.example.mdm_everis.login

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.core.Constant
import com.example.core.failure.Failure
import com.example.core.failure.UserFailure
import com.example.domain.devices.DevicesUseCase
import com.example.domain.sessionManage.LoginUseCase
import com.example.domain.reserves.DeviceReservesUseCase
import com.example.domain.reserves.UserReservesUseCase
import com.example.domain.sessionManage.LoginResponse
import com.example.domain.sign_up.CreateUserUseCase
import com.example.domain.user.GetUserByIdUserCase
import com.example.domain.user.User
import com.example.domain.user.UserResponse
import com.example.mdm_everis.base.BaseViewModel

class LoginViewModel(application: Application,
                     getUserByIdUserCase: GetUserByIdUserCase,
                     devicesUseCase: DevicesUseCase,
                     userReservesUseCase: UserReservesUseCase,
                     deviceReservesUseCase: DeviceReservesUseCase,
                     createUserUseCase: CreateUserUseCase,
                     private val loginUseCase : LoginUseCase
) : BaseViewModel(application,getUserByIdUserCase,devicesUseCase,userReservesUseCase,deviceReservesUseCase,createUserUseCase){

    //********************************** LiveData **************************************************


    private val loginMLD = MutableLiveData<LoginResponse>()
    val loginLD : LiveData<LoginResponse> = loginMLD

    private val loginFailureMLD = MutableLiveData<Failure>()
    val loginFailureLD : LiveData<Failure> = loginFailureMLD

    //********************************** End LiveData **********************************************

    //********************************** Login *****************************************************


    fun login(email: String,password: String){
        loadingMLD.value=true
        loginUseCase(User(email, password)) { it.fold(
            ::handleFailureLogin,
            ::handleSuccessLogin)
        }
    }

    private fun handleSuccessLogin(loginResponse: LoginResponse) {
        loginMLD.value = loginResponse
    }

    private fun handleFailureLogin(failure: Failure){
        loadingMLD.value = false
        loginFailureMLD.value = failure
    }





}