package com.example.mdm_everis.sign_up

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.core.failure.Failure
import com.example.domain.user.User
import com.example.domain.devices.DevicesUseCase
import com.example.domain.reserves.DeviceReservesUseCase
import com.example.domain.reserves.UserReservesUseCase
import com.example.domain.sign_up.CreateUserUseCase
import com.example.domain.sign_up.SignUpUseCase
import com.example.domain.user.GetUserByIdUserCase
import com.example.domain.user.UserResponse
import com.example.mdm_everis.base.BaseViewModel

class SignUpViewModel(application: Application,
                      getUserByIdUserCase: GetUserByIdUserCase,
                      devicesUseCase: DevicesUseCase,
                      userReservesUseCase: UserReservesUseCase,
                      deviceReservesUseCase: DeviceReservesUseCase,
                      private val signUpUseCase: SignUpUseCase,
                      private val createUserUseCase: CreateUserUseCase) :
    BaseViewModel(application,getUserByIdUserCase,devicesUseCase,userReservesUseCase,deviceReservesUseCase) {

    //********************************** LiveData **************************************************

    private val signUpMLD = MutableLiveData<String>()
    val signUpLD : LiveData<String> = signUpMLD

    private val createUserMLD = MutableLiveData<String>()
    val createUserLD : LiveData<String> = createUserMLD

    private val signUpFailureMLD = MutableLiveData<Failure>()
    val signUpFailureLD : LiveData<Failure> = signUpFailureMLD

    //********************************** End LiveData **********************************************

    //********************************** Sign up ***************************************************

    fun signUp(email : String, password : String){
        loadingMLD.value = true
        signUpUseCase(User(email, password)){it.fold(
            ::handleFailureSignUp,
            ::handleSuccessSignUp
        )
        }

    }

    private fun handleSuccessSignUp( user : String){
        signUpMLD.value = user
        Log.i("User",user)
    }

    private fun handleFailureSignUp( failure : Failure){
        loadingMLD.value = false
        signUpFailureMLD.value = failure
        Log.i("failure", failure.toString())
    }

    //********************************** End Sign up ***********************************************

    //********************************** Create User ***********************************************

    fun createUser(user : UserResponse){
        createUserUseCase(user){
            it.fold(
                ::handleFailureCreateUser,
                ::handleSuccessCreateUser
            )
        }
    }

    private fun handleFailureCreateUser(failure: Failure){
        loadingMLD.value = false
        signUpFailureMLD.value = failure
    }

    private fun handleSuccessCreateUser(s: String){
        loadingMLD.value = false
        createUserMLD.value = s
    }

    //********************************** End Create User *******************************************

}