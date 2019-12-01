package com.example.mdm_everis.home.profile

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.core.failure.Failure
import com.example.domain.devices.DevicesUseCase
import com.example.domain.reserves.DeviceReservesUseCase
import com.example.domain.reserves.UserReservesUseCase
import com.example.domain.sessionManage.SignOutUseCase
import com.example.domain.sign_up.CreateUserUseCase
import com.example.domain.user.GetUserByIdUserCase
import com.example.mdm_everis.base.BaseViewModel

class ProfileViewModel(application: Application,
                       getUserByIdUserCase: GetUserByIdUserCase,
                       devicesUseCase: DevicesUseCase,
                       userReservesUseCase: UserReservesUseCase,
                       deviceReservesUseCase: DeviceReservesUseCase,
                       createUserUseCase: CreateUserUseCase,
                       private val signOutUseCase: SignOutUseCase) :
    BaseViewModel(application,getUserByIdUserCase,devicesUseCase,userReservesUseCase,deviceReservesUseCase,createUserUseCase) {

    //********************************** LiveData **************************************************

    private val signOutMLD = MutableLiveData<Boolean>()
    val signOutLD : LiveData<Boolean> = signOutMLD

    private val signOutFailureMLD = MutableLiveData<Failure>()
    val signOutFailureLD : LiveData<Failure> = signOutFailureMLD

    //********************************** End LiveData **********************************************

    //********************************** Sign out **************************************************

    fun signOut(){
        signOutUseCase(Unit){
            it.fold(
                ::handleFailureSignOut,
                ::handleSuccessSignOut
            )
        }
    }

    private fun handleFailureSignOut(failure: Failure){
        signOutFailureMLD.value = failure
    }

    private fun handleSuccessSignOut(b: Boolean) {
        signOutMLD.value = b
    }

    //********************************** End Sign out **********************************************

}
