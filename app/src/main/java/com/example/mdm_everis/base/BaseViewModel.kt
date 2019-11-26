package com.example.mdm_everis.base

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.core.Constant
import com.example.core.failure.Failure
import com.example.domain.devices.DevicesResponse
import com.example.domain.devices.DevicesUseCase
import com.example.domain.reserves.DeviceReservesUseCase
import com.example.domain.reserves.ReserveResponse
import com.example.domain.reserves.UserReservesUseCase
import com.example.domain.user.GetUserByIdUserCase
import com.example.domain.user.UserResponse

abstract class BaseViewModel(application : Application,
                             private val getUserByIdUserCase: GetUserByIdUserCase,
                             private val devicesUseCase: DevicesUseCase,
                             private val userReservesUseCase: UserReservesUseCase,
                             private val deviceReservesUseCase: DeviceReservesUseCase
): AndroidViewModel(application) {

    //********************************** LiveData **************************************************

    var fragmentFlag = ""
    val loadingMLD = MutableLiveData<Boolean>()


    private val getUserByIdMLD = MutableLiveData<UserResponse>()
    val getUserByIdLD : LiveData<UserResponse> = getUserByIdMLD

    private val devicesMLD = MutableLiveData<List<DevicesResponse>>()
    val devicesLD : LiveData<List<DevicesResponse>> = devicesMLD

    private val userReservesMLD = MutableLiveData<List<ReserveResponse>?>()
    val userReservesLD : LiveData<List<ReserveResponse>?> = userReservesMLD

    private val failureMLD = MutableLiveData<Failure>()
    val failureLD : LiveData<Failure> = failureMLD

    private val deviceReservesMLD = MutableLiveData<List<ReserveResponse>>()
    val deviceReservesLD : LiveData<List<ReserveResponse>> = deviceReservesMLD

    //********************************** End LiveData **********************************************

    //********************************** Get User **************************************************

    fun getUserById( userId : String ){
        if (fragmentFlag != Constant.FragmentFlag.LOGIN){
            loadingMLD.value = true
        }
        getUserByIdUserCase(userId){
            it.fold(
                ::handleFailGetUserById,
                ::handleSuccessGetUserById
            )
        }
    }

    private fun handleSuccessGetUserById(userResponse: UserResponse?){
        if (fragmentFlag != Constant.FragmentFlag.LOGIN){
            loadingMLD.value = false
        }
        getUserByIdMLD.value = userResponse
    }

    private fun handleFailGetUserById(failure: Failure){
        if (fragmentFlag != Constant.FragmentFlag.LOGIN){
            loadingMLD.value = false
        }
        getUserByIdMLD.value = null
        Log.e("ErrorGetUserById", failure.toString())
    }

    //********************************** End Get User **********************************************

    //********************************** Get Devices ***********************************************

    fun allDevices(){
        if (fragmentFlag != Constant.FragmentFlag.LOGIN){
            loadingMLD.value = true
        }
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
        Log.e("ErrorGetAllDevices", failure.toString())
    }

    private fun handleSuccessAllDevices(allDevices : List<DevicesResponse>?){
        loadingMLD.value = false
        devicesMLD.value = allDevices
    }

    //********************************** End Get Devices *******************************************

    //********************************** Get User Reserves *****************************************

    fun getUserReserves(userId : String){
        if (fragmentFlag != Constant.FragmentFlag.LOGIN ||
                fragmentFlag != Constant.FragmentFlag.RESERVE_PROCESS){
            loadingMLD.value = true
        }
        userReservesUseCase(userId){
            it.fold(
                ::handleFailureUserReserves,
                ::handleSuccessUserReserves) }
    }

    private fun handleFailureUserReserves(failure: Failure){
        if (fragmentFlag != Constant.FragmentFlag.LOGIN){
            loadingMLD.value = false
        }
        failureMLD.value = failure
    }

    private fun handleSuccessUserReserves(list: List<ReserveResponse>?){
        if (fragmentFlag != Constant.FragmentFlag.LOGIN){
            loadingMLD.value = false
        }
        userReservesMLD.value = list
    }

    //********************************** End Get User Reserves *************************************

    //********************************** GET Device Reserves ***************************************

    fun deviceReserves(deviceId : String){
        loadingMLD.value = true
        deviceReservesUseCase(deviceId){
            it.fold(
                ::handleFailureDeviceReserves,
                ::handleSuccessDeviceReserves
            )
        }
    }

    private fun handleFailureDeviceReserves(failure: Failure){
        loadingMLD.value = false
        failureMLD.value = failure
    }

    private fun handleSuccessDeviceReserves(list: List<ReserveResponse>?) {
        loadingMLD.value = false
        deviceReservesMLD.value = list
    }

    //********************************** End GET Device Reserves ***********************************



}