package com.example.mdm_everis.login

import android.app.AlertDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import com.example.mdm_everis.MainActivity

import com.example.mdm_everis.R
import com.example.mdm_everis.base.BaseFragment
import com.example.mdm_everis.home.HomeFragment
import com.example.mdm_everis.navigateTo
import kotlinx.android.synthetic.main.fragment_login.*


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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
