package com.example.mdm_everis.home.reserves

import android.app.Application
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.core.failure.Failure
import com.example.domain.devices.DevicesUseCase
import com.example.domain.login.LoginUseCase
import com.example.domain.reserves.ReserveResponse
import com.example.domain.reserves.UserReservesUseCase
import com.example.domain.user.GetUserByIdUserCase
import com.example.domain.user.UserResponse
import com.example.mdm_everis.base.BaseViewModel

class ReservesViewModel(application: Application,
                        getUserByIdUserCase: GetUserByIdUserCase,
                        devicesUseCase: DevicesUseCase,
                        userReservesUseCase: UserReservesUseCase) :
    BaseViewModel(application,getUserByIdUserCase,devicesUseCase,userReservesUseCase) {


    //********************************** LiveData **************************************************
    /*
    private val userReservesMLD = MutableLiveData<List<ReserveResponse>?>()
    val userReservesLD : LiveData<List<ReserveResponse>?> = userReservesMLD

    private val failureMLD = MutableLiveData<Failure>()
    val failureLD : LiveData<Failure> = failureMLD

    //********************************** End LiveData **********************************************

    fun getUserReserves(userId : String){
        userReservesUseCase(userId){
            it.fold(
                ::handleFailureUserReserves,
                ::handleSuccessUserReserves) }
    }

    private fun handleFailureUserReserves(failure: Failure){
        failureMLD.value = failure
    }

    private fun handleSuccessUserReserves(list: List<ReserveResponse>?){
        userReservesMLD.value = list
    }




     */
     */


}
