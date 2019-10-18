package com.example.mdm_everis.home.dispositivos

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.domain.devices.DevicesResponse

import com.example.mdm_everis.R
import com.example.mdm_everis.base.BaseFragment
import com.example.mdm_everis.home.DispositivosAdapter
import kotlinx.android.synthetic.main.dispositivos_fragment.*

class DispositivosFragment : BaseFragment<DispositivosViewModel>() {

    companion object{
        fun setArguments() = bundleOf()
    }

    override fun getLayout() = R.layout.dispositivos_fragment
    override fun getViewModel() = DispositivosViewModel::class
    override val showToolbar = false



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showNavbar(true)
        initObservers()
        initListener()
    }

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
            rv_dispositivos.adapter = DispositivosAdapter(it, false)
            rv_dispositivos.layoutManager = LinearLayoutManager(context)
        }
    }



}
