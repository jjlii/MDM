package com.example.mdm_everis.home.devices

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.core.Constant
import com.example.domain.devices.DevicesResponse
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
    var devices : List<DevicesResponse>? = null


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
        devices?.let {
            rv_devices.adapter = DevicesAdapter(it, Constant.AdapterFlag.DEVICES,(activity as MainActivity).getFavoritesId())
            rv_devices.layoutManager = LinearLayoutManager(context)
        }?: run{
            toast("Error al cargar los dispositivos")
        }
    }

}
