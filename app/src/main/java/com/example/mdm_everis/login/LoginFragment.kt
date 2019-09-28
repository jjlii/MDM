package com.example.mdm_everis.login

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer

import com.example.mdm_everis.R
import com.example.mdm_everis.base.BaseFragment
import com.example.mdm_everis.base.BaseViewModel
import kotlin.reflect.KClass


class LoginFragment : BaseFragment<LoginViewModel>() {
    override fun getLayout() = R.layout.fragment_login

    override fun getViewModel(): KClass<LoginViewModel> = LoginViewModel::class

    override val showToolbar = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
    }


    private fun initObservers(){
        viewModel.getLoginLD.observe(this,loginObserver)
    }

    private val loginObserver = Observer<String> {

        if(it =="Success"){

        }else{

        }

    }



}
