package com.example.data

import com.example.core.Network
import com.example.domain.login.LoginRepository
import org.koin.dsl.module.module

class KoinData {
    val dataModule by lazy {
        module{
            single<LoginRepository>{ LoginRepositoryImp()}
        }
    }
}