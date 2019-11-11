package com.example.mdm_everis.login

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.core.Constant
import com.example.domain.devices.DevicesResponse
import com.example.domain.user.UserResponse
import com.example.mdm_everis.Devices
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

    lateinit var user : UserResponse

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showNavbar(false)
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
        viewModel.loginLD.observe(this,loginObserver)
        viewModel.getUserByIdLD.observe(this,getUserByIdObserver)
        viewModel.devicesLD.observe(this,devicesObserver)
    }

    //******************************************* End Init *****************************************

    //******************************************* Observers ****************************************

    private val loginObserver = Observer<String> {
        when(it){
            Constant.ErrorLogin.ERROR_CONEXION -> toast(it)
            Constant.ErrorLogin.NO_EXISTE_USUARIO -> toast(it)
            Constant.ErrorLogin.FORMATO_EMAIL_INCORRECTO -> toast(it)
            Constant.ErrorLogin.CONTRESENIA_INCORRECTA -> toast(it)
            Constant.ErrorGeneral.ERROR_DESCONOCIDO -> toast(it)
            Constant.ErrorLogin.EMAIL_NO_VERIFIED -> toast(it)
            else ->{
                viewModel.getUserById(it)
            }
        }
    }

    private  val getUserByIdObserver = Observer<UserResponse>{
        it?.let {
            user = it
            (activity as MainActivity).setUser(user)
        } ?: run{
            toast("Se ha habido un error al obtener el usuario")
        }
        viewModel.allDevies()
    }

    private val devicesObserver = Observer<List<DevicesResponse>>{
        var devices : Devices
        it?.apply {
            devices = Devices(it)
            findNavController().navigate(LoginFragmentDirections.actionLoginToHome(user = user,devices = devices))
        }?: run{
            devices = Devices(arrayListOf())
            findNavController().navigate(LoginFragmentDirections.actionLoginToHome(user = user,devices = devices))
            toast("Error")
        }
    }

    //******************************************* End Observers ************************************




}
