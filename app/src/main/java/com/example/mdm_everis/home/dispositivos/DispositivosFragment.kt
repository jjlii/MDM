package com.example.mdm_everis.home.dispositivos

import android.app.Application
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf

import com.example.mdm_everis.R
import com.example.mdm_everis.base.BaseFragment
import kotlin.reflect.KClass

class DispositivosFragment : BaseFragment<DispositivosViewModel>() {
    override fun getLayout() = R.layout.dispositivos_fragment

    override fun getViewModel() = DispositivosViewModel::class

    override val showToolbar = false

    companion object{
        fun setArguments() = bundleOf()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showNavbar(true)
    }





}
