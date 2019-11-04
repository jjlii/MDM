package com.example.mdm_everis

import com.example.mdm_everis.home.reserves.ReservesViewModel
import com.example.mdm_everis.home.devices.DevicesViewModel
import com.example.mdm_everis.home.favourites.FavouritesViewModel
import com.example.mdm_everis.home.profile.ProfileViewModel
import com.example.mdm_everis.login.LoginViewModel
import com.example.mdm_everis.sign_up.SignUpViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

class KoinApp {

    val appModule by lazy {
        module {
            viewModel { LoginViewModel(androidApplication(),get()) }
            viewModel { ReservesViewModel(androidApplication(),get()) }
            viewModel { FavouritesViewModel(androidApplication()) }
            viewModel { DevicesViewModel(androidApplication(),get()) }
            viewModel { SignUpViewModel(androidApplication(),get()) }
            viewModel { ProfileViewModel(androidApplication()) }
        }
    }
}