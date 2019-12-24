package com.example.mdm_everis.home.devices

import android.app.Application
import com.example.domain.devices.DevicesResponse
import com.example.domain.devices.DevicesUseCase
import com.example.domain.reserves.DeviceReservesUseCase
import com.example.domain.reserves.UserReservesUseCase
import com.example.domain.sign_up.CreateUserUseCase
import com.example.domain.user.GetUserByIdUseCase
import com.example.domain.user.UserResponse
import com.example.mdm_everis.Categories
import com.example.mdm_everis.base.BaseViewModel

class DevicesViewModel(application: Application,
                       getUserByIdUseCase: GetUserByIdUseCase,
                       devicesUseCase: DevicesUseCase,
                       userReservesUseCase: UserReservesUseCase,
                       deviceReservesUseCase: DeviceReservesUseCase,
                       createUserUseCase: CreateUserUseCase
) : BaseViewModel(application,getUserByIdUseCase,devicesUseCase,userReservesUseCase,deviceReservesUseCase,createUserUseCase) {

    var filterDevices : List<DevicesResponse> = arrayListOf()
    lateinit var categories : Categories

    fun categoriesFilter(devices : List<DevicesResponse>){
        when{
            categories.android && !categories.ios && !categories.phone && !categories.tablet ||
                    categories.android && !categories.ios && categories.phone && categories.tablet->{
                filterDevices = devices.filter {
                    it.so == "ANDROID"
                }
            }
            !categories.android && categories.ios && !categories.phone && !categories.tablet ||
                    !categories.android && categories.ios && categories.phone && categories.tablet-> {
                filterDevices = devices.filter {
                    it.so == "IOS"
                }
            }
            !categories.android && !categories.ios && categories.phone && !categories.tablet ||
                    categories.android && categories.ios && categories.phone && !categories.tablet-> {
                filterDevices = devices.filter {
                    it.isMobile
                }
            }
            !categories.android && !categories.ios && !categories.phone && categories.tablet ||
                    categories.android && categories.ios && !categories.phone && categories.tablet-> {
                filterDevices = devices.filter {
                    !it.isMobile
                }
            }
            categories.android && categories.ios && !categories.phone && !categories.tablet -> {
                filterDevices = devices.filter {
                    it.so == "ANDROID" || it.so == "IOS"
                }
            }
            categories.android && !categories.ios && categories.phone && !categories.tablet -> {
                filterDevices = devices.filter {
                    it.so == "ANDROID" && it.isMobile
                }
            }
            categories.android && !categories.ios && !categories.phone && categories.tablet -> {
                filterDevices = devices.filter {
                    it.so == "ANDROID" && !it.isMobile
                }
            }
            !categories.android && categories.ios && categories.phone && !categories.tablet -> {
                filterDevices = devices.filter {
                    it.so == "IOS" && it.isMobile
                }
            }
            !categories.android && categories.ios && !categories.phone && categories.tablet -> {
                filterDevices = devices.filter {
                    it.so == "IOS" && !it.isMobile
                }
            }
            else->{
                filterDevices = devices
            }
        }
    }

    fun favoriteAction(deviceId : String, user : UserResponse) : UserResponse{
        val newFavorites = user.favourites
        when(newFavorites.contains(deviceId)){
            true -> newFavorites.remove(deviceId)
            false -> newFavorites.add(deviceId)
        }
        user.favourites = newFavorites
        return user
    }

}
