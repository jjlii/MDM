package com.example.mdm_everis

import com.example.mdm_everis.device_details.DeviceDetailsViewModel
import com.example.mdm_everis.device_details.reserve.ReserveProcessViewModel
import com.example.mdm_everis.home.reserves.ReservesViewModel
import com.example.mdm_everis.home.devices.DevicesViewModel
import com.example.mdm_everis.home.favorites.FavoritesViewModel
import com.example.mdm_everis.home.profile.ProfileViewModel
import com.example.mdm_everis.login.LoginViewModel
import com.example.mdm_everis.sign_up.SignUpViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

class KoinApp {

    val appModule by lazy {
        module {
            viewModel { LoginViewModel(androidApplication(),get(),get(),get(),get()) }
            viewModel { ReservesViewModel(androidApplication(),get(),get(),get()) }
            viewModel { FavoritesViewModel(androidApplication(),get(),get(),get()) }
            viewModel { DevicesViewModel(androidApplication(),get(),get(),get()) }
            viewModel { SignUpViewModel(androidApplication(),get(),get(),get(),get()) }
            viewModel { ProfileViewModel(androidApplication(),get(),get(),get()) }
            viewModel { DeviceDetailsViewModel(androidApplication(),get(),get(),get()) }
            viewModel { ReserveProcessViewModel(androidApplication(),get(),get(),get(),get()) }
        }
    }
}