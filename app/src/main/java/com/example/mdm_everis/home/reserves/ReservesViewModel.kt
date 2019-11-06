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

    //********************************** LiveData **************************************************

    //********************************** End LiveData **********************************************

}
