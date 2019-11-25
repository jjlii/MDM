package com.example.mdm_everis.home.reserves

import android.app.Application
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.core.failure.Failure
import com.example.domain.devices.DevicesUseCase
import com.example.domain.login.LoginUseCase
import com.example.domain.reserves.*
import com.example.domain.user.GetUserByIdUserCase
import com.example.domain.user.UserResponse
import com.example.mdm_everis.base.BaseViewModel

class ReservesViewModel(application: Application,
                        getUserByIdUserCase: GetUserByIdUserCase,
                        devicesUseCase: DevicesUseCase,
                        userReservesUseCase: UserReservesUseCase,
                        deviceReservesUseCase: DeviceReservesUseCase,
                        private val deleteReserveUseCase: DeleteReserveUseCase) :
    BaseViewModel(application,getUserByIdUserCase,devicesUseCase,userReservesUseCase,deviceReservesUseCase) {


    //********************************** LiveData **************************************************

    private val deleteReserveMLD = MutableLiveData<ReserveResponse>()
    val deleteReserveLD : LiveData<ReserveResponse> = deleteReserveMLD

    private val reserveFailureMLD = MutableLiveData<Failure>()
    val reserveFailure : LiveData<Failure> = reserveFailureMLD

    //********************************** End LiveData **********************************************

    //********************************** Delete Reserve ********************************************

    fun deleteReserve(deviceId : String,reserveId : String){
        loadingMLD.value = true
        deleteReserveUseCase(DeleteReserveReq(deviceId,reserveId)){
            it.fold(
                ::handleFailureDeleteReserve,
                ::handleSuccessDeleteReserve
            )
        }
    }

    private fun handleFailureDeleteReserve(failure: Failure){
        loadingMLD.value = false
        reserveFailureMLD.value = failure
    }

    private fun handleSuccessDeleteReserve(reserveResponse: ReserveResponse?){
        loadingMLD.value = false
        deleteReserveMLD.value = reserveResponse
    }

    //********************************** End Delete Reserve ****************************************




}
