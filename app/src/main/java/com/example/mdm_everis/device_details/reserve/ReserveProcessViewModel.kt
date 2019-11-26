package com.example.mdm_everis.device_details.reserve

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.core.failure.Failure
import com.example.domain.devices.DevicesUseCase
import com.example.domain.login.LoginUseCase
import com.example.domain.reserves.*
import com.example.domain.user.GetUserByIdUserCase
import com.example.mdm_everis.base.BaseViewModel

class ReserveProcessViewModel(application: Application,
                              getUserByIdUserCase: GetUserByIdUserCase,
                              devicesUseCase: DevicesUseCase,
                              userReservesUseCase: UserReservesUseCase,
                              deviceReservesUseCase: DeviceReservesUseCase,
                              private val createReserveUseCase: CreateReserveUseCase)
    : BaseViewModel(application,getUserByIdUserCase,devicesUseCase,userReservesUseCase,deviceReservesUseCase) {

    //********************************** LiveData **************************************************

    private val createReserveMLD = MutableLiveData<ReserveResponse>()
    val createReserveLD : LiveData<ReserveResponse> = createReserveMLD

    private val createReserveFailureMLD = MutableLiveData<Failure>()
    val createReserveFailure : LiveData<Failure> = createReserveFailureMLD

    //********************************** End LiveData **********************************************

    //********************************** Create reserve ********************************************

    fun createNewReserve(reserve : ReserveResponse,deviceId : String){
        loadingMLD.value = true
        createReserveUseCase(CreateReserveReq(reserve,deviceId)){
            it.fold(
                ::createReserveFailure,
                ::createReserveSuccess
            )
        }
    }

    private fun createReserveFailure(failure: Failure){
        loadingMLD.value = false
        createReserveFailureMLD.value = failure
    }

    private fun createReserveSuccess(reserveResponse: ReserveResponse?) {
        createReserveMLD.value = reserveResponse
    }

    //********************************** End Create reserve ****************************************

}
