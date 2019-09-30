package com.example.domain

import com.example.domain.login.LoginUseCase
import org.koin.dsl.module.module

class KoinDomain {
    val domainModule by lazy {
        module{
            factory { LoginUseCase(get()) }
        }
    }
}