package com.example.mdm_everis.home.reserves


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf

import com.example.mdm_everis.R
import com.example.mdm_everis.base.BaseFragment

class ReservesFragment : BaseFragment<ReservesViewModel>() {

    companion object {
        fun setArguments() = bundleOf()
    }

    override fun getLayout() = R.layout.reserves_fragment
    override fun getViewModel() = ReservesViewModel::class
    override val showToolbar: Boolean = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showNavbar(true)
        navbar.menu.getItem(0).isChecked = true
        /*
        initObservers()
        initListener()

         */
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.reserves_fragment, container, false)
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
