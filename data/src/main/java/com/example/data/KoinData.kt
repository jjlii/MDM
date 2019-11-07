package com.example.data

import com.example.core.Network
import com.example.data.repository_imp.DevicesRepositoryImp
import com.example.data.repository_imp.GetDeviceByIdRepositoryImp
import com.example.data.repository_imp.LoginRepositoryImp
import com.example.data.repository_imp.SignUpRepositoryImp
import com.example.data.retrofit.DevicesRetrofit
import com.example.data.retrofit.UserRetrofit
import com.example.domain.repository.DevicesRepository
import com.example.domain.repository.GetDeviceByIdRepository
import com.example.domain.repository.UserRepository
import com.example.domain.login.LoginRepository
import com.example.domain.sign_up.SignUpRepository
import org.koin.dsl.module.module

class KoinData {
    val dataModule by lazy {
        module{
            single<LoginRepository>{ LoginRepositoryImp() }
            single{Network.initRetrofit().create(DevicesRetrofit :: class.java)}
            single{Network.initRetrofit().create(UserRetrofit :: class.java)}
            single<DevicesRepository>{ DevicesRepositoryImp(get()) }
            single<SignUpRepository>{ SignUpRepositoryImp() }
            single<UserRepository>{ UserRepositoryImp(get())}
            single<GetDeviceByIdRepository>{
                GetDeviceByIdRepositoryImp(
                    get()
                )
            }
        }
    }
}