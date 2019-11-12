package com.example.mdm_everis.home.favorites


import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.core.failure.Failure
import com.example.domain.devices.DevicesResponse
import com.example.domain.devices.GetDeviceByIdUseCase
import com.example.mdm_everis.base.BaseViewModel

class FavoritesViewModel(application: Application,
                         private val getDeviceByIdUseCase: GetDeviceByIdUseCase) : BaseViewModel(application) {

    //********************************** LiveData **************************************************
    //********************************** End LiveData **********************************************

    //********************************** Get Favorite Devices **************************************
    //********************************** End Get Favorite Devices **********************************

}
