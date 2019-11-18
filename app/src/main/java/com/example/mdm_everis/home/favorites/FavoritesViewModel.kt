package com.example.mdm_everis.home.favorites


import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.core.failure.Failure
import com.example.domain.devices.DevicesResponse
import com.example.domain.devices.DevicesUseCase
import com.example.domain.devices.GetDeviceByIdUseCase
import com.example.domain.login.LoginUseCase
import com.example.domain.reserves.UserReservesUseCase
import com.example.domain.user.GetUserByIdUserCase
import com.example.mdm_everis.base.BaseViewModel

class FavoritesViewModel(application: Application,
                         getUserByIdUserCase: GetUserByIdUserCase,
                         devicesUseCase: DevicesUseCase,
                         userReservesUseCase: UserReservesUseCase) :
    BaseViewModel(application,getUserByIdUserCase,devicesUseCase,userReservesUseCase) {

    //********************************** LiveData **************************************************
    //********************************** End LiveData **********************************************

    //********************************** Get Favorite Devices **************************************
    //********************************** End Get Favorite Devices **********************************

}
