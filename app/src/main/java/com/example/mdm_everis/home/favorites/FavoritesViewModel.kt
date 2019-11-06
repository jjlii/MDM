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

    private val favoriteMLD = MutableLiveData<List<DevicesResponse?>>()
    val favoriteLD : LiveData<List<DevicesResponse?>> = favoriteMLD

    //********************************** End LiveData **********************************************

    lateinit var favorites : MutableList <DevicesResponse?>
    private var index = 0
    //********************************** Get Favorite Devices **************************************

    fun getFavoriteDevices(devicesId : List<String>){
        loadingMLD.value = true
        devicesId.forEach {id ->
            getDeviceByIdUseCase(id){
                it.fold(
                    ::handleFailureGetDevice,
                    ::handleSuccessGetDevice
                )
            }
        }
        favoriteMLD.value = favorites
        loadingMLD.value = false
    }

    private fun handleFailureGetDevice(failure: Failure){
        favoriteMLD.value = null
        Log.e("handleFailureGetDevice",failure.toString())
    }

    private fun handleSuccessGetDevice(devicesResponse: DevicesResponse?){
        favorites.add(index,devicesResponse)
    }

    //********************************** End Get Favorite Devices **********************************

}
