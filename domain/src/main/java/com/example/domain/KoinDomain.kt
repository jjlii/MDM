package com.example.domain

import com.example.domain.devices.DevicesUseCase
import com.example.domain.devices.GetDeviceByIdUseCase
import com.example.domain.login.LoginUseCase
import com.example.domain.reserves.DeviceReservesUseCase
import com.example.domain.reserves.UserReservesUseCase
import com.example.domain.sign_up.CreateUserUseCase
import com.example.domain.sign_up.SignUpUseCase
import com.example.domain.user.GetUserByIdUserCase
import org.koin.dsl.module.module

class KoinDomain {
    val domainModule by lazy {
        module{
            factory { LoginUseCase(get()) }
            factory { DevicesUseCase(get()) }
            factory { SignUpUseCase(get()) }
            factory { CreateUserUseCase(get()) }
            factory { GetUserByIdUserCase(get()) }
            factory { GetDeviceByIdUseCase(get()) }
            factory { UserReservesUseCase(get()) }
            factory { DeviceReservesUseCase(get()) }
        }
    }
}