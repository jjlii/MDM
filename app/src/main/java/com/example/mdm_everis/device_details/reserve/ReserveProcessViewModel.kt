package com.example.mdm_everis.device_details.reserve

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

class ReserveProcessViewModel(application: Application,
                              getUserByIdUserCase: GetUserByIdUserCase,
                              devicesUseCase: DevicesUseCase,
                              userReservesUseCase: UserReservesUseCase)
    : BaseViewModel(application,getUserByIdUserCase,devicesUseCase,userReservesUseCase) {



}
