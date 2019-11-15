package com.example.mdm_everis.home.reserves


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.core.Constant
import com.example.core.failure.Failure
import com.example.domain.devices.DevicesResponse
import com.example.domain.reserves.ReserveResponse
import com.example.domain.user.UserResponse
import com.example.mdm_everis.Devices
import com.example.mdm_everis.MainActivity

import com.example.mdm_everis.R
import com.example.mdm_everis.base.BaseFragment
import com.example.mdm_everis.home.DevicesAdapter
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.reserves_fragment.*

class ReservesFragment : BaseFragment<ReservesViewModel>() {

    //******************************************* BaseFragment abstract ****************************

    override fun getLayout() = R.layout.reserves_fragment
    override fun getViewModel() = ReservesViewModel::class

    //******************************************* End BaseFragment abstract ************************

    private val args : ReservesFragmentArgs by navArgs()
    private val navBar by lazy {
        (activity as MainActivity).getNavBar()
    }
    lateinit var user : UserResponse
    lateinit var devices: Devices

    private var myReserves : MutableList<DevicesResponse> = arrayListOf()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        user = (activity as MainActivity).getUser()
        devices = args.devices
        showNavbar(true)
        navBar.menu.getItem(0).isChecked = true
        navBar.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        showAdapter()
    }

    //******************************************* Init *********************************************



    //******************************************* End Init *****************************************

    //******************************************* Bottom Nav Bar ***********************************

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { menuItem ->
        when (menuItem.itemId) {
            R.id.nav_reserves -> {
                findNavController().popBackStack(R.id.reserves_screen,false)
                return@OnNavigationItemSelectedListener true
            }
            R.id.nav_favourites -> {
                findNavController().popBackStack(R.id.reserves_screen,false)
                findNavController().navigate(ReservesFragmentDirections.actionToFavorites(devices))
                return@OnNavigationItemSelectedListener true
            }
            R.id.nav_devices -> {
                findNavController().popBackStack(R.id.reserves_screen,false)
                findNavController().navigate(ReservesFragmentDirections.actionToDevices(devices))
                return@OnNavigationItemSelectedListener true
            }
            R.id.nav_profile -> {
                findNavController().popBackStack(R.id.reserves_screen,false)
                findNavController().navigate(ReservesFragmentDirections.actionToProfile())
                return@OnNavigationItemSelectedListener true
            }
        }
        false

    }

    //******************************************* EndBottom Nav Bar ********************************

    //******************************************* Observers ****************************************

    private fun showAdapter (){
        myReserves = arrayListOf()
        val userReserve = (activity as MainActivity).getUserReserves()
        getMyReserves(userReserve)
        rv_reserves.adapter = DevicesAdapter(myReserves,userReserve,Constant.AdapterFlag.RESERVES,user.favourites,{
                deviceId, _->
            favoriteAction(deviceId)
        },{deviceId->
            findNavController().navigate(ReservesFragmentDirections.actionReservesToDeviceDetails(
                Devices(navigateToDetails(deviceId,devices.allDevices))
            ))
        })
        rv_reserves.layoutManager = LinearLayoutManager(context)

    }
    //******************************************* End Observers ************************************




    private fun favoriteAction(deviceId : String){
        val newFavorites = user.favourites
        when(newFavorites.contains(deviceId)){
            true -> newFavorites.remove(deviceId)
            false -> newFavorites.add(deviceId)
        }
        user.favourites = newFavorites
        (activity as MainActivity).setUser(user)
    }

    private fun getMyReserves(reservesResponse : List<ReserveResponse>?){
        devices.allDevices.forEach { device->
            reservesResponse?.forEach {reserve->
                if (device.id == reserve.deviceId){
                    myReserves.add(device)
                }
            }
        }
    }
}
