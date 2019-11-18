package com.example.mdm_everis.home.profile

import android.app.Application
import androidx.lifecycle.ViewModel
import com.example.domain.devices.DevicesUseCase
import com.example.domain.login.LoginUseCase
import com.example.domain.reserves.UserReservesUseCase
import com.example.domain.user.GetUserByIdUserCase
import com.example.mdm_everis.base.BaseViewModel

class ProfileViewModel(application: Application,
                       getUserByIdUserCase: GetUserByIdUserCase,
                       devicesUseCase: DevicesUseCase,
                       userReservesUseCase: UserReservesUseCase) :
    BaseViewModel(application,getUserByIdUserCase,devicesUseCase,userReservesUseCase) {

    //********************************** LiveData **************************************************

    //********************************** End LiveData **********************************************

}
