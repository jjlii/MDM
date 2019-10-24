package com.example.mdm_everis.home.reserves

import android.app.Application
import com.example.domain.devices.DevicesUseCase
import com.example.mdm_everis.base.BaseViewModel

class ReservesViewModel(application: Application,
                        private val devicesUseCase : DevicesUseCase) : BaseViewModel(application) {
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
