package com.example.mdm_everis.login

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.core.Constant
import com.example.core.failure.Failure
import com.example.core.failure.UserFailure
import com.example.domain.devices.DevicesResponse
import com.example.domain.reserves.ReserveResponse
import com.example.domain.sessionManage.LoginResponse
import com.example.domain.user.UserResponse
import com.example.mdm_everis.MainActivity

import com.example.mdm_everis.R
import com.example.mdm_everis.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.login_card_view.*


class LoginFragment : BaseFragment<LoginViewModel>() {

    //******************************************* BaseFragment abstract ****************************

    override fun getLayout() = R.layout.fragment_login
    override fun getViewModel()= LoginViewModel::class

    //******************************************* End BaseFragment abstract ************************

    var userId = ""
    var notificationToken = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showNavbar(false)
        (activity as MainActivity).setFragment(Constant.FragmentFlag.LOGIN)
        initObservers()
        initListener()
    }

    //******************************************* Init *********************************************

    private fun initListener(){
        btn_login.setOnClickListener {
            when {
                etUsername.text.toString() == "" -> etUsername.error = "Debes introducir el usuario"
                etPwd.text.toString() == "" -> etPwd.error = "Debes instroducir la contraseña"
                else ->{
                    viewModel.login(etUsername.text.toString()+
                            Constant.GeneralConstant.EVERIS_EMAIL_EXTENSIONS,etPwd.text.toString())
                }
            }
        }
        btnSignup.setOnClickListener {
            findNavController().navigate(LoginFragmentDirections.actionLoginToSignUp())
        }
        etPwd.setOnEditorActionListener{
            _,_,_ ->
            btn_login.callOnClick()
        }
    }

    private fun initObservers(){
        viewModel.fragmentFlag = Constant.FragmentFlag.LOGIN
        viewModel.loginLD.observe(this,loginObserver)
        viewModel.loginFailureLD.observe(this,loginErrorObserver)
        viewModel.getUserByIdLD.observe(this,getUserByIdObserver)
        viewModel.devicesLD.observe(this,devicesObserver)
        viewModel.userReservesLD.observe(this,getUserReserveObserver)
        viewModel.failureLD.observe(this,errorObserver)
    }

    //******************************************* End Init *****************************************

    //******************************************* Observers ****************************************

    private val loginObserver = Observer<LoginResponse> {
        userId = it.userUid
        notificationToken = it.notificationToken
        viewModel.getUserById(it.userUid)
        viewModel.getUserReserves(it.userUid)
    }

    private  val getUserByIdObserver = Observer<UserResponse>{
        it?.let {
            if(it.notificationToken != notificationToken){
                it.notificationToken = notificationToken
            }
            userChanged = true
            (activity as MainActivity).setUser(it)
        } ?: run{
            toast("Se ha habido un error al obtener el usuario")
        }
        viewModel.allDevices()
    }

    private val devicesObserver = Observer<List<DevicesResponse>>{
        findNavController().navigate(LoginFragmentDirections.actionLoginToHome(userId))
        it?.apply {
            (activity as MainActivity).setDevice(it)
        }
    }

    private val getUserReserveObserver = Observer<List<ReserveResponse>?>{
        it?.let {
            (activity as MainActivity).setUserReserves(it)
        }
    }

    private val errorObserver = Observer<Failure>{
        it?.let {
            toast(it.toString())
        }
    }

    private val loginErrorObserver= Observer<Failure>{
        when(it){
            UserFailure.InvalidPassword -> toast(Constant.ErrorLogin.CONTRESENIA_INCORRECTA)
            UserFailure.InvalidEmailFormat -> toast(Constant.ErrorLogin.FORMATO_EMAIL_INCORRECTO)
            UserFailure.InvalidEmail-> toast(Constant.ErrorLogin.NO_EXISTE_USUARIO)
            UserFailure.EmailNoVerified -> toast(Constant.ErrorLogin.EMAIL_NO_VERIFIED)
            Failure.NetworkConnection -> toast(Constant.ErrorLogin.ERROR_CONEXION)
            Failure.Unknown-> toast(Constant.ErrorGeneral.ERROR_DESCONOCIDO)
        }
    }

    //******************************************* End Observers ************************************




}
