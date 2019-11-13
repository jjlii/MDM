package com.example.mdm_everis.home.devices

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.core.Constant
import com.example.domain.devices.DevicesResponse
import com.example.domain.user.UserResponse
import com.example.mdm_everis.Devices
import com.example.mdm_everis.MainActivity

import com.example.mdm_everis.R
import com.example.mdm_everis.base.BaseFragment
import com.example.mdm_everis.home.DevicesAdapter
import kotlinx.android.synthetic.main.devices_fragment.*

class DevicesFragment : BaseFragment<DevicesViewModel>() {

    //******************************************* BaseFragment abstract ****************************

    override fun getLayout() = R.layout.devices_fragment
    override fun getViewModel() = DevicesViewModel::class

    //******************************************* End BaseFragment abstract ************************

    private val args : DevicesFragmentArgs by navArgs()
    var devices : List<DevicesResponse> = arrayListOf()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showNavbar(true)
        baseNavBar.menu.getItem(2).isChecked = true
        devices = args.devices.allDevices
        showAdapter()
    }

    //******************************************* Init *********************************************
    //******************************************* End Init *****************************************

    //******************************************* Observers ****************************************
    //******************************************* End Observers ************************************

    private fun showAdapter(){
        val user = (activity as MainActivity).getUser()
        devices.let {
            rv_devices.adapter = DevicesAdapter(it,null, Constant.AdapterFlag.DEVICES,
                user.favourites,{ deviceId,_->
                favoriteAction(deviceId)
            },{deviceId ->
                    findNavController().navigate(DevicesFragmentDirections.actionDevicesToDeviceDetails(
                        Devices(navigateToDetails(deviceId,devices))))
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
        (activity as MainActivity).setUser(user)
    }


}
