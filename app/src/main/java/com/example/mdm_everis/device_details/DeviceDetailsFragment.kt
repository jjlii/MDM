package com.example.mdm_everis.device_details

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.example.domain.devices.DevicesResponse
import com.example.mdm_everis.R
import com.example.mdm_everis.base.BaseFragment
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.device_details_fragment.*
import kotlinx.android.synthetic.main.devices_items.view.*

class DeviceDetailsFragment : BaseFragment<DeviceDetailsViewModel>() {

    //******************************************* BaseFragment abstract ****************************

    override fun getLayout() = R.layout.device_details_fragment
    override fun getViewModel() = DeviceDetailsViewModel::class

    //******************************************* End BaseFragment abstract ************************

    private val args : DeviceDetailsFragmentArgs by navArgs()
    var device : DevicesResponse? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showNavbar(false)
        device = args.device.allDevices[0]
        setData()
    }

    //******************************************* Init *********************************************
    //******************************************* End Init *****************************************

    //******************************************* Observers ****************************************
    //******************************************* End Observers ************************************

    private fun setData(){
        val mobile = "${device?.brand} ${device?.model}"
        val so = "${device?.so} ${device?.version}"
        when(device?.isMobile){
            true->typeDevice_c.text = "Móvil"
            false->typeDevice_c.text = "Tablet"
        }
        tv_device_name.text = mobile
        so_c.text = so
        screenSize_c.text = device?.screenSize
        screenResolution_c.text = device?.screenResolution
        ppi_c.text = device?.ppi
        when(device?.sim){
            true-> sim_c.text = "Sí"
            false-> sim_c.text = "No"
        }
        typeCharger_c.text = device?.typeCharger
        Picasso.get().load(device?.picture).into(im_device)
    }
}
