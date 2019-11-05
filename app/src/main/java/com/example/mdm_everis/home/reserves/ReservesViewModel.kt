package com.example.mdm_everis.home.reserves

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.core.failure.Failure
import com.example.domain.devices.DevicesUseCase
import com.example.domain.user.GetUserByIdUserCase
import com.example.domain.user.UserResponse
import com.example.mdm_everis.base.BaseViewModel

class ReservesViewModel(application: Application,
                        private val devicesUseCase : DevicesUseCase,
                        private val getUserByIdUserCase: GetUserByIdUserCase) : BaseViewModel(application) {

    private val getUserByIdMLD = MutableLiveData<UserResponse>()
    val getUserByIdLD : LiveData<UserResponse> = getUserByIdMLD

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
    /*
    private val devicesMLD = MutableLiveData<List<DevicesResponse>>()
    val devicesLD : LiveData<List<DevicesResponse>> = devicesMLD


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

    private fun handleSuccessAllDevices(allDevices : List<DevicesResponse>){
        loadingMLD.value = false
        devicesMLD.value = allDevices
    }

     */
}
