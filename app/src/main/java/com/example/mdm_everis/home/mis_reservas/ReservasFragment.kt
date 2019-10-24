package com.example.mdm_everis.home.mis_reservas


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.domain.devices.DevicesResponse

import com.example.mdm_everis.R
import com.example.mdm_everis.base.BaseFragment
import kotlinx.android.synthetic.main.reservas_fragment.*

class ReservasFragment : BaseFragment<ReservasViewModel>() {

    companion object {
        fun setArguments() = bundleOf()
    }

    override fun getLayout() = R.layout.reservas_fragment
    override fun getViewModel() = ReservasViewModel::class
    override val showToolbar: Boolean = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showNavbar(true)
        /*
        initObservers()
        initListener()

         */
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.reservas_fragment, container, false)
    }

/*

    private fun initObservers(){
        viewModel.devicesLD.observe(this,reservasObserver)
    }

    private fun initListener(){
        viewModel.allDevies()
    }

    private val reservasObserver = Observer<List<DevicesResponse>>{
        if (it == null){
            toast("Error")
        }else{
            rv_reservas.adapter = ReservasAdapter(it)
            rv_reservas.layoutManager = LinearLayoutManager(context)
        }
    }

 */





}
