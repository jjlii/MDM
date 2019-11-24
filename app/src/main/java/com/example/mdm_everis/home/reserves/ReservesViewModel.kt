package com.example.mdm_everis.home.reserves

import android.app.Application
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.core.failure.Failure
import com.example.domain.devices.DevicesUseCase
import com.example.domain.login.LoginUseCase
import com.example.domain.reserves.DeviceReservesUseCase
import com.example.domain.reserves.ReserveResponse
import com.example.domain.reserves.UserReservesUseCase
import com.example.domain.user.GetUserByIdUserCase
import com.example.domain.user.UserResponse
import com.example.mdm_everis.base.BaseViewModel

class ReservesViewModel(application: Application,
                        getUserByIdUserCase: GetUserByIdUserCase,
                        devicesUseCase: DevicesUseCase,
                        userReservesUseCase: UserReservesUseCase,
                        deviceReservesUseCase: DeviceReservesUseCase) :
    BaseViewModel(application,getUserByIdUserCase,devicesUseCase,userReservesUseCase,deviceReservesUseCase) {


    //********************************** LiveData **************************************************

    //********************************** End LiveData **********************************************



}
