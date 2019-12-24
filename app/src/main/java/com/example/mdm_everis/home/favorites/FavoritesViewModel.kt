package com.example.mdm_everis.home.favorites


import android.app.Application
import com.example.domain.devices.DevicesResponse
import com.example.domain.devices.DevicesUseCase
import com.example.domain.reserves.DeviceReservesUseCase
import com.example.domain.reserves.UserReservesUseCase
import com.example.domain.sign_up.CreateUserUseCase
import com.example.domain.user.GetUserByIdUseCase
import com.example.mdm_everis.base.BaseViewModel

class FavoritesViewModel(application: Application,
                         getUserByIdUseCase: GetUserByIdUseCase,
                         devicesUseCase: DevicesUseCase,
                         userReservesUseCase: UserReservesUseCase,
                         deviceReservesUseCase: DeviceReservesUseCase,
                         createUserUseCase: CreateUserUseCase) :
    BaseViewModel(application,getUserByIdUseCase,devicesUseCase,userReservesUseCase,deviceReservesUseCase,createUserUseCase) {

    var favorites : MutableList<DevicesResponse> = arrayListOf()

    fun getFavoriteDevices(favoritesId : MutableList<String>,
                           devices : List<DevicesResponse>){
        var index = 0
        favorites.clear()
        favoritesId.forEach {id ->
            favorites.add(index,
                devices.single {
                    it.id == id
                }
            )
            index++
        }
    }

}
