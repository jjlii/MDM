package com.example.mdm_everis.reset_pwd

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.core.failure.Failure
import com.example.domain.devices.DevicesUseCase
import com.example.domain.reserves.DeviceReservesUseCase
import com.example.domain.reserves.UserReservesUseCase
import com.example.domain.sign_up.CreateUserUseCase
import com.example.domain.user.GetUserByIdUseCase
import com.example.domain.user.ResetPwdUseCase
import com.example.mdm_everis.base.BaseViewModel

class ResetPwdViewModel(application: Application,
                        getUserByIdUseCase: GetUserByIdUseCase,
                        devicesUseCase: DevicesUseCase,
                        userReservesUseCase: UserReservesUseCase,
                        deviceReservesUseCase: DeviceReservesUseCase,
                        createUserUseCase: CreateUserUseCase,
                        private val resetPwdUseCase: ResetPwdUseCase
) : BaseViewModel(application,getUserByIdUseCase,devicesUseCase,userReservesUseCase,deviceReservesUseCase,createUserUseCase) {

    //********************************** LiveData **************************************************

    private val resetPwdMLD = MutableLiveData<String>()
    val resetPwdLD : LiveData<String> = resetPwdMLD

    private val resetFailureMLD = MutableLiveData<Failure>()
    val resetFailureLD : LiveData<Failure> = resetFailureMLD

    //********************************** End LiveData **********************************************

    fun resetPwd(email:String){
        resetPwdUseCase(email){
            it.fold(
                ::handleFailureResetPwd,
                ::handleSuccessResetPwd
            )
        }
    }

    private fun handleFailureResetPwd(failure: Failure) {
        resetFailureMLD.value = failure
    }

    private fun handleSuccessResetPwd(msg: String) {
        resetPwdMLD.value = msg
    }

}
