package com.example.mdm_everis.home.devices

import android.app.Application
import com.example.domain.devices.DevicesUseCase
import com.example.domain.reserves.DeviceReservesUseCase
import com.example.domain.reserves.UserReservesUseCase
import com.example.domain.sign_up.CreateUserUseCase
import com.example.domain.user.GetUserByIdUserCase
import com.example.mdm_everis.base.BaseViewModel

class DevicesViewModel(application: Application,
                       getUserByIdUserCase: GetUserByIdUserCase,
                       devicesUseCase: DevicesUseCase,
                       userReservesUseCase: UserReservesUseCase,
                       deviceReservesUseCase: DeviceReservesUseCase,
                       createUserUseCase: CreateUserUseCase
) : BaseViewModel(application,getUserByIdUserCase,devicesUseCase,userReservesUseCase,deviceReservesUseCase,createUserUseCase) {

    //********************************** LiveData **************************************************

    //********************************** End LiveData **********************************************

}
