package com.example.mdm_everis.sign_up

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.core.Constant
import com.example.mdm_everis.R
import com.example.mdm_everis.base.BaseFragment
import kotlinx.android.synthetic.main.sign_up_card_view.*


class SignUpFragment : BaseFragment<SignUpViewModel>() {

    override fun getLayout() = R.layout.fragment_sign_up

    override fun getViewModel() = SignUpViewModel::class

    override val showToolbar = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showNavbar(false)
        setToolbarTitle("")
        initListener()
        initObservers()
    }

    private fun initListener(){
        btn_registrate.setOnClickListener{
            when{
                et_full_name.text.toString() == "" -> et_full_name.error = Constant.ErrorSignUp.NOT_NAME
                et_email.text.toString() == "" -> et_email.error = Constant.ErrorSignUp.NOT_EVERIS_EMAIL
                et_password.text.toString() == "" -> et_password.error = Constant.ErrorSignUp.NOT_PWD
                et_rep_password.text.toString() == "" -> et_rep_password.error = Constant.ErrorSignUp.NOT_REP_PWD
                et_password.text.toString() != et_rep_password.text.toString() -> et_rep_password.error = Constant.ErrorSignUp.NOT_EQUAL_PWD
                else ->{
                    if (et_password.text?.length!! >= 8){
                        viewModel.signUp(et_email.text.toString()+
                                Constant.GeneralConstant.EVERIS_EMAIL_EXTENSIONS,et_password.text.toString())
                    }else{
                        et_password.error = Constant.ErrorSignUp.PWD_TOO_EASY
                    }
                }
            }


        }
    }

    private fun initObservers(){
        viewModel.signUpLD.observe(this,signUpObserver)
    }

    private val signUpObserver = Observer<String>{
        if(it != "Error"){
            val alertDialog = AlertDialog.Builder(context)
            alertDialog.setTitle("Correo de verificación")
            alertDialog.setMessage("Ve a tu correo de EVERIS y verifica tu correo. Si no lo has recibido el correo revisa tus datos.")
            alertDialog.setPositiveButton("Sí, ir al login"
            ) { _, _ ->
                findNavController().navigate(SignUpFragmentDirections.actionSignUpToLogin())
            }
            alertDialog.setNeutralButton("Cancelar",null)
            alertDialog.show()
        }else{
            toast(Constant.ErrorSignUp.ERROR_REGISTRO)
        }
    }



}