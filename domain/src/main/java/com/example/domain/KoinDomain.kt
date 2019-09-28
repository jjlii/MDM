package com.example.domain

import com.example.domain.login.LoginUserCase
import org.koin.dsl.module.module

class KoinDomain {
    val domainModule by lazy {
        module{
            factory { LoginUserCase(get()) }
        }
    }
}