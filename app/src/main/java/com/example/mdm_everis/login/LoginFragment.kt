package com.example.mdm_everis.login

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.core.Constant

import com.example.mdm_everis.R
import com.example.mdm_everis.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.login_card_view.*


class LoginFragment : BaseFragment<LoginViewModel>() {

    override fun getLayout() = R.layout.fragment_login

    override fun getViewModel()= LoginViewModel::class

    override val showToolbar = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showNavbar(false)
        setToolbarTitle("")
        initObservers()
        initListener()
    }

    private fun initObservers(){
        viewModel.loginLD.observe(this,loginObserver)
    }

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

    private val loginObserver = Observer<String> {
        when(it){
            Constant.ErrorLogin.ERROR_CONEXION -> toast(it)
            Constant.ErrorLogin.NO_EXISTE_USUARIO -> toast(it)
            Constant.ErrorLogin.FORMATO_EMAIL_INCORRECTO -> toast(it)
            Constant.ErrorLogin.CONTRESENIA_INCORRECTA -> toast(it)
            Constant.ErrorGeneral.ERROR_DESCONOCIDO -> toast(it)
            Constant.ErrorLogin.EMAIL_NO_VERIFIED -> toast(it)
            else ->{
                findNavController().navigate(LoginFragmentDirections.actionLoginToHome(it))
            }
        }
    }









}
