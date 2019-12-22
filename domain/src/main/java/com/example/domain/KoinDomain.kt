package com.example.domain

import com.example.domain.devices.DevicesUseCase
import com.example.domain.devices.GetDeviceByIdUseCase
import com.example.domain.sessionManage.LoginUseCase
import com.example.domain.reserves.*
import com.example.domain.sessionManage.SignOutUseCase
import com.example.domain.sign_up.CreateUserUseCase
import com.example.domain.sign_up.SignUpUseCase
import com.example.domain.user.GetUserByIdUseCase
import com.example.domain.user.ResetPwdUseCase
import org.koin.dsl.module.module

class KoinDomain {
    val domainModule by lazy {
        module{
            factory { LoginUseCase(get()) }
            factory { DevicesUseCase(get()) }
            factory { SignUpUseCase(get()) }
            factory { CreateUserUseCase(get()) }
            factory { GetUserByIdUseCase(get()) }
            factory { GetDeviceByIdUseCase(get()) }
            factory { UserReservesUseCase(get()) }
            factory { DeviceReservesUseCase(get()) }
            factory { CreateReserveUseCase(get()) }
            factory { DeleteReserveUseCase(get()) }
            factory { CreateCaducatedReserveUseCase(get()) }
            factory { SignOutUseCase(get()) }
            factory { ResetPwdUseCase(get()) }
        }
    }
}