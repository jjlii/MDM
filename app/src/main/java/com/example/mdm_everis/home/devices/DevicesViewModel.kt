package com.example.mdm_everis.home.devices

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.core.failure.Failure
import com.example.domain.devices.DevicesResponse
import com.example.domain.devices.DevicesUseCase
import com.example.mdm_everis.base.BaseViewModel

class DevicesViewModel(application: Application, private val devicesUseCase : DevicesUseCase) : BaseViewModel(application) {

}
