package com.example.mdm_everis.login

import android.app.AlertDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.core.Constant

import com.example.mdm_everis.R
import com.example.mdm_everis.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.login_card_view.*


class LoginFragment : BaseFragment<LoginViewModel>() {

    private var nfcDialog : AlertDialog? =null

    override fun getLayout() = R.layout.fragment_login

    override fun getViewModel()= LoginViewModel::class

    override val showToolbar = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showNavbar(false)
        setToolbarTitle("")
        initObservers()
        iniListener()
    }

    private fun initObservers(){
        viewModel.loginLD.observe(this,loginObserver)
    }

    private fun iniListener(){
        btnAcceder.setOnClickListener {
            when {
                etUsername.text.toString() == "" -> etUsername.error = "Debes introducir el usuario"
                etPwd.text.toString() == "" -> etPwd.error = "Debes instroducir la contraseña"
                else -> viewModel.login(etUsername.text.toString(),etPwd.text.toString())
            }
        }
        btnReadCard.setOnClickListener {
            showNFCDialog()
        }
        btnWriteCard.setOnClickListener {

        }
        etPwd.setOnEditorActionListener{
            _,_,_ ->
            btnAcceder.callOnClick()
        }
    }

    private val loginObserver = Observer<String> {
        when(it){
            Constant.ErrorLogin.ERROR_CONEXION -> toast(it)
            Constant.ErrorLogin.NO_EXISTE_USUARIO -> toast(it)
            Constant.ErrorLogin.FORMATO_EMAIL_INCORRECTO -> toast(it)
            Constant.ErrorLogin.CONTRESENIA_INCORRECTA -> toast(it)
            Constant.ErrorGeneral.ERROR_DESCONOCIDO -> toast(it)
            else ->{
                findNavController().navigate(LoginFragmentDirections.actionLoginToHome(it))
            }
        }
    }

    private fun Fragment.createNFCDialog(): AlertDialog?{
        return this.activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.setView(R.layout.fullscrren_nfc_dialog)
            builder.setCancelable(true)
            builder.create().apply {
                window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            }
        }
    }


    private fun showNFCDialog(){
        nfcDialog = createNFCDialog()
        nfcDialog?.show()
    }




}
