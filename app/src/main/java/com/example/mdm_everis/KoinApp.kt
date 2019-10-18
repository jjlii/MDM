package com.example.mdm_everis

import com.example.mdm_everis.home.mis_reservas.ReservasViewModel
import com.example.mdm_everis.home.dispositivos.DispositivosViewModel
import com.example.mdm_everis.home.reservas_caducadas.ReservasCaducadasViewModel
import com.example.mdm_everis.login.LoginViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

class KoinApp {

    val appModule by lazy {
        module {
            viewModel { LoginViewModel(androidApplication(),get()) }
            viewModel { ReservasViewModel(androidApplication(),get()) }
            viewModel { ReservasCaducadasViewModel(androidApplication()) }
            viewModel { DispositivosViewModel(androidApplication(),get()) }
        }
    }
}