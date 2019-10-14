package com.example.mdm_everis.home.reservas_caducadas


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf

import com.example.mdm_everis.R
import com.example.mdm_everis.base.BaseFragment

class ReservasCaducadasFragment :BaseFragment<ReservasCaducadasViewModel>() {
    override fun getLayout() = R.layout.reservas_caducadas_fragment

    override fun getViewModel() = ReservasCaducadasViewModel::class

    override val showToolbar = false

    companion object{
        fun setArguments() = bundleOf()
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showNavbar(true)
    }






}
