package com.example.mdm_everis.home.devices

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.domain.devices.DevicesResponse

import com.example.mdm_everis.R
import com.example.mdm_everis.base.BaseFragment
import com.example.mdm_everis.home.DivicesAdapter
import kotlinx.android.synthetic.main.devices_fragment.*

class DevicesFragment : BaseFragment<DevicesViewModel>() {


    override fun getLayout() = R.layout.devices_fragment
    override fun getViewModel() = DevicesViewModel::class
    override val showToolbar = false



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showNavbar(true)
        navbar.menu.getItem(2).isChecked = true
        initObservers()
        initListener()
    }




    private fun initObservers(){
        viewModel.devicesLD.observe(this,devicesObserver)
    }

    private fun initListener(){
        viewModel.allDevies()
    }


    private val devicesObserver = Observer<List<DevicesResponse>>{
        if (it == null){
            toast("Error")
        }else{
            rv_devices.adapter = DivicesAdapter(it, false)
            rv_devices.layoutManager = LinearLayoutManager(context)
        }
    }



}
