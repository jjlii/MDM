package com.example.mdm_everis.home.devices

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.core.Constant
import com.example.core.failure.Failure
import com.example.domain.devices.DevicesResponse
import com.example.domain.reserves.ReserveResponse
import com.example.mdm_everis.parcelable_data.Devices
import com.example.mdm_everis.MainActivity

import com.example.mdm_everis.R
import com.example.mdm_everis.base.BaseFragment
import com.example.mdm_everis.home.DevicesAdapter
import com.example.mdm_everis.parcelable_data.Reserves
import kotlinx.android.synthetic.main.devices_fragment.*

class DevicesFragment : BaseFragment<DevicesViewModel>() {

    //******************************************* BaseFragment abstract ****************************

    override fun getLayout() = R.layout.devices_fragment
    override fun getViewModel() = DevicesViewModel::class

    //******************************************* End BaseFragment abstract ************************
    var devices : List<DevicesResponse> = arrayListOf()
    lateinit var selectDeviceId : String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as MainActivity).setFragment(Constant.FragmentFlag.DEVICES)
        viewModel.fragmentFlag = Constant.FragmentFlag.DEVICES
        showNavbar(true)
        baseNavBar.menu.getItem(2).isChecked = true
        devices = (activity as MainActivity).getDevice()
        initObserver()
        showAdapter()
        devices_refresh.setOnRefreshListener {
            devices_refresh.isRefreshing = false
            viewModel.allDevices()
        }
    }


    //******************************************* Init *********************************************

    private fun initObserver(){
        viewModel.devicesLD.observe(this,devicesObserver)
        viewModel.deviceReservesLD.observe(this,deviceReservesObserver)
        viewModel.failureLD.observe(this,failureObserver)
    }
    //******************************************* End Init *****************************************

    //******************************************* Observers ****************************************
    private val devicesObserver = Observer<List<DevicesResponse>>{
        it?.let {
            devices = it
            (activity as MainActivity).setDevice(it)
        }?: run{
            toast("Error al cargar los dispositivos")
        }
        showAdapter()
    }

    private val deviceReservesObserver = Observer<List<ReserveResponse>>{
        findNavController().navigate(
            DevicesFragmentDirections.actionDevicesToReserveProcess(
                Devices(getDeviceDetails(selectDeviceId,devices)),
                Reserves(it)
            )
        )
    }

    private val failureObserver = Observer<Failure>{
        toast(it.toString())
    }
    //******************************************* End Observers ************************************

    private fun showAdapter(){
        val user = (activity as MainActivity).getUser()
        devices.let {
            rv_devices.adapter = DevicesAdapter(it, arrayListOf(), Constant.FragmentFlag.DEVICES,
                user.favourites,{ deviceId,_->
                favoriteAction(deviceId)
            },{deviceId, _ ->
                    reserveAction(deviceId)
                },{deviceId ->
                    touchAction(deviceId)
            })
            rv_devices.layoutManager = LinearLayoutManager(context)
        }
    }

    private fun favoriteAction(deviceId : String){
        val user = (activity as MainActivity).getUser()
        val newFavorites = user.favourites
        when(newFavorites.contains(deviceId)){
            true -> newFavorites.remove(deviceId)
            false -> newFavorites.add(deviceId)
        }
        user.favourites = newFavorites
        rv_devices.adapter?.notifyDataSetChanged()
        (activity as MainActivity).setUser(user)
    }

    private fun reserveAction(deviceId: String){
        selectDeviceId = deviceId
        viewModel.deviceReserves(deviceId)
    }

    private fun touchAction(deviceId : String){
        findNavController().navigate(DevicesFragmentDirections.actionDevicesToDeviceDetails(
            Devices(
                getDeviceDetails(
                    deviceId,
                    devices
                )
            )
        ))
    }


}
