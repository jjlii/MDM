package com.example.mdm_everis.home.favorites


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
import com.example.mdm_everis.home.adapters.DevicesAdapter
import com.example.mdm_everis.parcelable_data.Reserves
import kotlinx.android.synthetic.main.devices_fragment.*
import kotlinx.android.synthetic.main.empty_list.*
import kotlinx.android.synthetic.main.error_network.*
import kotlinx.android.synthetic.main.favorites_fragment.*
import kotlinx.android.synthetic.main.favorites_fragment.ly_error

class FavoritesFragment :BaseFragment<FavoritesViewModel>() {

    //******************************************* BaseFragment abstract ****************************

    override fun getLayout() = R.layout.favorites_fragment
    override fun getViewModel() = FavoritesViewModel::class

    //******************************************* End BaseFragment abstract ************************
    private val args : FavoritesFragmentArgs by navArgs()
    var devices : List<DevicesResponse> = arrayListOf()
    lateinit var selectDeviceId : String
    lateinit var userId : String


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showNavbar(true)
        (activity as MainActivity).setFragment(Constant.FragmentFlag.FAVORITES)
        viewModel.fragmentFlag=Constant.FragmentFlag.FAVORITES
        baseNavBar.menu.getItem(1).isChecked = true
        devices = (activity as MainActivity).getDevice()
        userId = args.userId
        viewModel.getFavoriteDevices((activity as MainActivity).getUser().favourites,devices)
        when{
            devices.isEmpty()-> isErrorScreen()
            viewModel.favorites.isEmpty()-> isEmptyScreen()
            else -> showAdapter()
        }
        initObserves()
        initListener()
    }

    //******************************************* Init *********************************************

    private fun initListener(){
        viewModel.fragmentFlag = Constant.FragmentFlag.FAVORITES

        favorites_refresh.setOnRefreshListener {
            favorites_refresh.isRefreshing = false
            if (devices.isEmpty()){
                viewModel.allDevices()
            }
            viewModel.getUserById(userId)
        }
    }

    private fun initObserves(){
        viewModel.devicesLD.observe(this,devicesObserver)
        viewModel.getUserByIdLD.observe(this,getUserByIdObserver)
        viewModel.deviceReservesLD.observe(this,deviceReservesObserver)
        viewModel.failureLD.observe(this,failureObserver)
    }


    //******************************************* End Init *****************************************

    //******************************************* Observers ****************************************

    private  val getUserByIdObserver = Observer<UserResponse>{
        favorites_refresh.isRefreshing = false
        it?.let {
            (activity as MainActivity).setUser(it)
        } ?: run{
            toast("Se ha habido un error al obtener el usuario")
        }
        showAdapter()
    }

    private val devicesObserver = Observer<List<DevicesResponse>> {
        it?.let {
            devices = it
            (activity as MainActivity).setDevice(it)
        }?: run{
            toast("Error al cargar los dispositivos")
        }
    }

    private val deviceReservesObserver = Observer<List<ReserveResponse>>{
        findNavController().navigate(FavoritesFragmentDirections.actionFavoriteToReserveProcess(
            Devices(getDeviceDetails(selectDeviceId,devices)),
            Reserves(it))
        )
    }

    private val failureObserver = Observer<Failure>{
        toast(it.toString())
    }
    //******************************************* End Observers ************************************

    private fun showAdapter(){
        viewModel.favorites.let{
            rv_favorites.adapter = DevicesAdapter(it,
                arrayListOf(),
                Constant.FragmentFlag.FAVORITES,
                (activity as MainActivity).getUser().favourites,
                { deviceId, position ->
                    favoriteAction(deviceId, position)
                },
                { deviceId, _ ->
                    reserveAction(deviceId)
                },
                { deviceId ->
                    touchAction(deviceId)
                })
            rv_favorites.layoutManager = LinearLayoutManager(context)
        }
    }



    private fun favoriteAction(deviceId: String,position : Int){
        val user = (activity as MainActivity).getUser()
        val newFavorites = user.favourites
        if (newFavorites.contains(deviceId)){
            newFavorites.remove(deviceId)
            viewModel.favorites.removeAt(position)
            rv_favorites.adapter?.apply {
                notifyItemRemoved(position)
                notifyItemRangeChanged(position,newFavorites.size)
            }
            user.favourites = newFavorites
            userChanged = true
            (activity as MainActivity).setUser(user)
        }
        if (newFavorites.isEmpty()){
            isEmptyScreen()
        }
    }

    private fun reserveAction(deviceId: String){
        selectDeviceId = deviceId
        viewModel.deviceReserves(deviceId)
    }

    private fun touchAction(deviceId: String){
        findNavController().navigate(FavoritesFragmentDirections.actionFavoriteToDeviceDetails(
            Devices(
                getDeviceDetails(
                    deviceId,
                    devices
                )
            )
        ))
    }

    private fun isEmptyScreen(){
        rv_favorites.visibility = View.GONE
        ly_empty_list.visibility = View.VISIBLE
        ly_error.visibility = View.GONE
        empty_msg.visibility = View.GONE
        lottie_empty_list.visibility = View.GONE
        lottie_empty_favorites.visibility = View.VISIBLE
    }

    private fun isErrorScreen(){
        rv_favorites.visibility = View.GONE
        ly_empty_list.visibility = View.GONE
        ly_error.visibility = View.VISIBLE
        error_msg.text = Constant.Msg.ERROR_LOAD_DEVICES
    }

}
