package com.example.mdm_everis.home.reserves


import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.core.Constant
import com.example.core.failure.Failure
import com.example.domain.devices.DevicesResponse
import com.example.domain.reserves.ReserveResponse
import com.example.domain.user.UserResponse
import com.example.mdm_everis.parcelable_data.Devices
import com.example.mdm_everis.MainActivity

import com.example.mdm_everis.R
import com.example.mdm_everis.base.BaseFragment
import com.example.mdm_everis.home.DevicesAdapter
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.reserves_fragment.*
import java.util.*

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
    private var devices: List<DevicesResponse> = arrayListOf()
    lateinit var userId : String
    lateinit var deviceDeletedName : String

    private var myReserves : MutableList<DevicesResponse> = arrayListOf()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as MainActivity).setFragment(Constant.FragmentFlag.RESERVES)
        viewModel.fragmentFlag = Constant.FragmentFlag.RESERVES
        initObservers()
        user = (activity as MainActivity).getUser()
        devices = (activity as MainActivity).getDevice()
        userId = args.userId
        showNavbar(true)
        navBar.menu.getItem(0).isChecked = true
        navBar.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        reserves_refresh.setOnRefreshListener {
            reserves_refresh.isRefreshing = false
            if (devices.isEmpty()){
                viewModel.allDevices()
            }
            viewModel.getUserReserves(user.id)
        }
        showAdapter()
    }

    //******************************************* Init *********************************************

    private fun initObservers(){
        viewModel.userReservesLD.observe(this,getUserReserveObserver)
        viewModel.failureLD.observe(this,errorObserver)
        viewModel.devicesLD.observe(this,devicesObserver)
        viewModel.deleteReserveLD.observe(this,deleteReserveObserver)
    }


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
                findNavController().navigate(ReservesFragmentDirections.actionToFavorites(userId))
                return@OnNavigationItemSelectedListener true
            }
            R.id.nav_devices -> {
                findNavController().popBackStack(R.id.reserves_screen,false)
                findNavController().navigate(ReservesFragmentDirections.actionToDevices())
                return@OnNavigationItemSelectedListener true
            }
            R.id.nav_profile -> {
                findNavController().popBackStack(R.id.reserves_screen,false)
                findNavController().navigate(ReservesFragmentDirections.actionToProfile(userId))
                return@OnNavigationItemSelectedListener true
            }
        }
        false

    }

    //******************************************* EndBottom Nav Bar ********************************

    //******************************************* Observers ****************************************

    private val getUserReserveObserver = Observer<List<ReserveResponse>?>{
        it?.let {
            (activity as MainActivity).setUserReserves(it)
        }
        showAdapter()
    }

    private val errorObserver = Observer<Failure>{
        it?.let {
            toast(it.toString())
        }
    }

    private val devicesObserver = Observer<List<DevicesResponse>> {
        it?.let {
            devices = it
            (activity as MainActivity).setDevice(it)
        }?: run{
            toast("Error al cargar los dispositivos")
        }
    }

    private val deleteReserveObserver = Observer<ReserveResponse>{
        val newEndDate = Calendar.getInstance()
        val sDate : String = convertLongToDate(it.startDate.toLong(),"dd/MM/yyyy HH:mm")
        val eDate : String = convertLongToDate(newEndDate.timeInMillis,"dd/MM/yyyy HH:mm")
        val msg = "Se ha cancelado la reserva:\n" +
                "Dispositivo: ${deviceDeletedName}\n"+
                "Fecha incio reserva: ${sDate}\n"+
                "Fecha fin de reserva: ${eDate}"
        it.endDate = newEndDate.timeInMillis.toString()
        val alertDialog = AlertDialog.Builder(context)
        alertDialog.setTitle("Reserva cancelada")
        alertDialog.setMessage(msg)

        alertDialog.setPositiveButton("Aceptar"){ _,_ ->
            showAdapter()
        }
    }

    //******************************************* End Observers ************************************


    private fun showAdapter (){
        myReserves = arrayListOf()
        val userReserve = (activity as MainActivity).getUserReserves()
        getMyReserves(userReserve)
        rv_reserves.adapter = DevicesAdapter(myReserves,userReserve,Constant.FragmentFlag.RESERVES,user.favourites,{
                deviceId, _->
            favoriteAction(deviceId)
        },{deviceId, reserve ->
            reserveAction(deviceId,reserve)
        },{deviceId->
            touchAction(deviceId)
        })
        rv_reserves.layoutManager = LinearLayoutManager(context)

    }

    private fun favoriteAction(deviceId : String){
        val newFavorites = user.favourites
        when(newFavorites.contains(deviceId)){
            true -> newFavorites.remove(deviceId)
            false -> newFavorites.add(deviceId)
        }
        user.favourites = newFavorites
        rv_reserves.adapter?.notifyDataSetChanged()
        (activity as MainActivity).setUser(user)
    }

    private fun reserveAction(deviceId: String,reserve : ReserveResponse?){
        val alertDialog = AlertDialog.Builder(context)
        val allDevices = (activity as MainActivity).getDevice()
        val d = getDeviceDetails(deviceId,allDevices)[0]
        deviceDeletedName = "${d.brand} ${d.model}"
        alertDialog.setTitle("Cancelar reserva")
        alertDialog.setMessage("¿Estás seguro de que quiere cancelar la reserva del dispositivo \"${deviceDeletedName}\"?")
        alertDialog.setPositiveButton("Aceptar"){_,_ ->
            reserve?.let {
                viewModel.deleteReserve(deviceId,it.id)
            }
        }
        alertDialog.setNeutralButton("Cancelar",null)
        alertDialog.show()
    }

    private fun touchAction(deviceId: String){
        findNavController().navigate(ReservesFragmentDirections.actionReservesToDeviceDetails(
            Devices(
                getDeviceDetails(
                    deviceId,
                    devices
                )
            )
        ))
    }

    private fun getMyReserves(reservesResponse : List<ReserveResponse>?){
        reservesResponse?.forEach {reserve ->
            devices.forEach{device ->
                if (device.id == reserve.deviceId)
                    myReserves.add(device)
            }
        }
    }

}
