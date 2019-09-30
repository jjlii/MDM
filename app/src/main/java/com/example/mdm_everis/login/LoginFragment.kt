package com.example.mdm_everis.login

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer

import com.example.mdm_everis.R
import com.example.mdm_everis.base.BaseFragment
import com.example.mdm_everis.base.BaseViewModel
import com.example.mdm_everis.home.HomeFragment
import com.example.mdm_everis.navigateTo
import kotlinx.android.synthetic.main.fragment_login.*
import kotlin.reflect.KClass


class LoginFragment : BaseFragment<LoginViewModel>() {

    private var nfcDialog : AlertDialog? =null

    companion object{
        fun setArguments() = bundleOf()
    }


    override fun getLayout() = R.layout.fragment_login

    override fun getViewModel()= LoginViewModel::class

    override val showToolbar = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setToolbarTitle("")
        initObservers()
        iniListener()
    }




    private fun initObservers(){
        viewModel.getLoginLD.observe(this,loginObserver)
    }

    private fun iniListener(){
        btnAcceder.setOnClickListener {
            when {
                etUsername.text.toString() == "" -> etUsername.error = "Debes introducir el usuario"
                etPwd.text.toString() == "" -> etPwd.error = "Debes instroducir la contraseña"
                else -> viewModel.login(etUsername.text.toString(),etPwd.text.toString())
            }
        }
        btnTarjeta.setOnClickListener {
            showNFCDialog()
        }
        etPwd.setOnEditorActionListener{
            _,_,_ ->
            btnAcceder.callOnClick()
        }
    }

    private val loginObserver = Observer<String> {

        if(it =="Success"){
            navigateTo(R.id.home_screen,HomeFragment.setArguments())
        }else{
            toast(it)
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
