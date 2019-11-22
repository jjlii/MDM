package com.example.mdm_everis.device_details

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.core.failure.Failure
import com.example.domain.devices.DevicesUseCase
import com.example.domain.login.LoginUseCase
import com.example.domain.reserves.DeviceReservesUseCase
import com.example.domain.reserves.ReserveResponse
import com.example.domain.reserves.UserReservesUseCase
import com.example.domain.user.GetUserByIdUserCase
import com.example.mdm_everis.base.BaseViewModel

class DeviceDetailsViewModel(application: Application,
                             getUserByIdUserCase: GetUserByIdUserCase,
                             devicesUseCase: DevicesUseCase,
                             userReservesUseCase: UserReservesUseCase,
                             private val deviceReservesUseCase: DeviceReservesUseCase
) : BaseViewModel(application,getUserByIdUserCase,devicesUseCase,userReservesUseCase) {

    //********************************** LiveData **************************************************

    private val deviceReservesMLD = MutableLiveData<List<ReserveResponse>>()
    val deviceReservesLD : LiveData<List<ReserveResponse>> = deviceReservesMLD

    private val failureMLD = MutableLiveData<Failure>()
    val reserveProcessFailureLD : LiveData<Failure> = failureMLD

    //********************************** End LiveData **********************************************

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
