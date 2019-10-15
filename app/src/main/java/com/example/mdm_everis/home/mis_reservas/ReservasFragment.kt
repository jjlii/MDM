package com.example.mdm_everis.home.mis_reservas


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf

import com.example.mdm_everis.R
import com.example.mdm_everis.base.BaseFragment
import kotlinx.android.synthetic.main.reservas_fragment.*

class ReservasFragment : BaseFragment<ReservasViewModel>() {
    override fun getLayout() = R.layout.reservas_fragment
    override fun getViewModel() = ReservasViewModel::class

    companion object {
        fun setArguments() = bundleOf()
    }

    override val showToolbar: Boolean
        = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.reservas_fragment, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showNavbar(true)
        rv_reservas.adapter = ReservasAdapter()
    }

}
