package com.example.mdm_everis.home.reserves

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.core.failure.Failure
import com.example.domain.devices.DevicesUseCase
import com.example.domain.reserves.ReserveResponse
import com.example.domain.reserves.UserReservesUseCase
import com.example.domain.user.GetUserByIdUserCase
import com.example.domain.user.UserResponse
import com.example.mdm_everis.base.BaseViewModel

class ReservesViewModel(application: Application,
                        private val userReservesUseCase: UserReservesUseCase) : BaseViewModel(application) {

    //********************************** LiveData **************************************************

    private val userReservesMLD = MutableLiveData<List<ReserveResponse>?>()
    val userReservesLD : LiveData<List<ReserveResponse>?> = userReservesMLD

    private val failureMLD = MutableLiveData<Failure>()
    val failureLD : LiveData<Failure> = failureMLD

    //********************************** End LiveData **********************************************

    fun getUserReserves(userId : String){
        loadingMLD.value = true
        userReservesUseCase(userId){
            it.fold(
                ::handleFailureUserReserves,
                ::handleSuccessUserReserves) }
    }

    private fun handleFailureUserReserves(failure: Failure){
        loadingMLD.value = false
        failureMLD.value = failure
    }

    private fun handleSuccessUserReserves(list: List<ReserveResponse>?){
        loadingMLD.value = false
        userReservesMLD.value = list
    }


}
