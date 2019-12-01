package com.example.mdm_everis.home.reserves

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.core.failure.Failure
import com.example.domain.devices.DevicesUseCase
import com.example.domain.reserves.*
import com.example.domain.sign_up.CreateUserUseCase
import com.example.domain.user.GetUserByIdUserCase
import com.example.mdm_everis.base.BaseViewModel

class ReservesViewModel(application: Application,
                        getUserByIdUserCase: GetUserByIdUserCase,
                        devicesUseCase: DevicesUseCase,
                        userReservesUseCase: UserReservesUseCase,
                        deviceReservesUseCase: DeviceReservesUseCase,
                        createUserUseCase: CreateUserUseCase,
                        private val deleteReserveUseCase: DeleteReserveUseCase,
                        private val createCaducatedReserveUseCase: CreateCaducatedReserveUseCase) :
    BaseViewModel(application,getUserByIdUserCase,devicesUseCase,userReservesUseCase,deviceReservesUseCase,createUserUseCase) {


    //********************************** LiveData **************************************************

    private val deleteReserveMLD = MutableLiveData<ReserveResponse>()
    val deleteReserveLD : LiveData<ReserveResponse> = deleteReserveMLD

    private val reserveFailureMLD = MutableLiveData<Failure>()
    val reserveFailureLD : LiveData<Failure> = reserveFailureMLD

    private val createCaducatedReserveMLD = MutableLiveData<String>()
    val createCaducatedReserveLD : LiveData<String> = createCaducatedReserveMLD

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
        deleteReserveMLD.value = reserveResponse
    }

    //********************************** End Delete Reserve ****************************************
    //********************************** Create Caducate Reserve ***********************************

    fun createCaducatedReserve(reserve : ReserveResponse,mostrarLoading: Boolean){
        loadingMLD.value = mostrarLoading
        createCaducatedReserveUseCase(reserve){
            it.fold(
                ::handleFailureCaducatedReserve,
                ::handleSuccessCaducatedReserve
            )
        }
    }

    private fun handleFailureCaducatedReserve(failure: Failure){
        loadingMLD.value = false
        reserveFailureMLD.value = failure
    }

    private fun handleSuccessCaducatedReserve(s: String?) {
        loadingMLD.value = false
        createCaducatedReserveMLD.value = s
    }

    //********************************** End create Caducate Reserve *******************************

}
