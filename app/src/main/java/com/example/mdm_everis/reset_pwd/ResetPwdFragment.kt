package com.example.mdm_everis.reset_pwd

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.core.Constant
import com.example.core.failure.Failure
import com.example.mdm_everis.R
import com.example.mdm_everis.base.BaseFragment
import kotlinx.android.synthetic.main.reset_pwd_fragment.*

class ResetPwdFragment : BaseFragment<ResetPwdViewModel>() {

    //******************************************* BaseFragment abstract ****************************

    override fun getLayout() = R.layout.reset_pwd_fragment
    override fun getViewModel()= ResetPwdViewModel::class

    //******************************************* End BaseFragment abstract ************************

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showNavbar(false)
        initListener()
        initObservers()
    }

    //******************************************* Init *********************************************

    private fun initListener(){
        btn_reset.setOnClickListener {
            val email = etEmail.text.toString()
            if (email != ""){
                viewModel.resetPwd(email+Constant.GeneralConstant.EVERIS_EMAIL_EXTENSIONS)
            }else{
                toast(Constant.GeneralConstant.NEED_EMAIL)
            }
        }
    }

    private fun initObservers(){
        viewModel.resetPwdLD.observe(this,resetPwdObserver)
        viewModel.resetFailureLD.observe(this,errorObserver)
    }

    //******************************************* End Init *****************************************

    //******************************************* Observers ****************************************

    private val resetPwdObserver = Observer<String>{
        val alertDialog = AlertDialog.Builder(context)
        if (it == "Success"){
            alertDialog.setTitle("Restablecer contraseña")
            alertDialog.setMessage("Se ha enviado el correo de reseteo de contraseña a su email")
            alertDialog.setPositiveButton("Aceptar"){
                _,_ ->
                findNavController().navigate(ResetPwdFragmentDirections.actionResetPwdToLogin())
            }
            alertDialog.show()
        }
    }

    private val errorObserver = Observer<Failure>{
        val alertDialog = AlertDialog.Builder(context)
        alertDialog.setTitle("Error")
        alertDialog.setMessage("No se ha podido enviar el email para restablecer la contraseña. Revisa el email que ha introducido.")
        alertDialog.setNegativeButton("Cancelar",null)
        alertDialog.show()
    }

    //******************************************* End Observers ************************************

}
