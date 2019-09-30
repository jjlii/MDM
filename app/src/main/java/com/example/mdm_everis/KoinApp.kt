package com.example.mdm_everis

import com.example.mdm_everis.login.LoginViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

class KoinApp {

    val appModule by lazy {
        module {
            viewModel { LoginViewModel(androidApplication(),get()) }
        }
    }
}