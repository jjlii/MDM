package com.example.mdm_everis.device_details

import android.app.Application
import com.example.domain.devices.DevicesUseCase
import com.example.domain.reserves.DeviceReservesUseCase
import com.example.domain.reserves.UserReservesUseCase
import com.example.domain.sign_up.CreateUserUseCase
import com.example.domain.user.GetUserByIdUserCase
import com.example.mdm_everis.base.BaseViewModel

class DeviceDetailsViewModel(application: Application,
                             getUserByIdUserCase: GetUserByIdUserCase,
                             devicesUseCase: DevicesUseCase,
                             userReservesUseCase: UserReservesUseCase,
                             deviceReservesUseCase: DeviceReservesUseCase,
                             createUserUseCase: CreateUserUseCase
) : BaseViewModel(application,getUserByIdUserCase,devicesUseCase,userReservesUseCase,deviceReservesUseCase,createUserUseCase) {


}
