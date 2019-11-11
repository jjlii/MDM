package com.example.mdm_everis.home.favorites


import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.core.Constant
import com.example.domain.devices.DevicesResponse
import com.example.mdm_everis.Devices
import com.example.mdm_everis.MainActivity

import com.example.mdm_everis.R
import com.example.mdm_everis.base.BaseFragment
import com.example.mdm_everis.home.DevicesAdapter
import com.example.mdm_everis.home.devices.DevicesFragmentDirections
import kotlinx.android.synthetic.main.favorites_fragment.*

class FavoritesFragment :BaseFragment<FavoritesViewModel>() {

    //******************************************* BaseFragment abstract ****************************

    override fun getLayout() = R.layout.favorites_fragment
    override fun getViewModel() = FavoritesViewModel::class

    //******************************************* End BaseFragment abstract ************************
    private val args : FavoritesFragmentArgs by navArgs()
    private var favorites : MutableList<DevicesResponse> = arrayListOf()
    var devices : List<DevicesResponse> = arrayListOf()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showNavbar(true)
        baseNavBar.menu.getItem(1).isChecked = true
        devices = args.devices.allDevices
        getFavoriteDevices((activity as MainActivity).getFavoritesId())
        showAdapter()
    }

    //******************************************* Init *********************************************

    private fun initListener(){
    }

    private fun initObserves(){
    }

    //******************************************* End Init *****************************************

    //******************************************* Observers ****************************************
    //******************************************* End Observers ************************************

    private fun showAdapter(){
        favorites.let{
            rv_favorites.adapter = DevicesAdapter(it,Constant.AdapterFlag.FAVORITES,
                (activity as MainActivity).getFavoritesId(),{ deviceId,position->
                    favoriteAction(deviceId,position)
                },{ deviceId ->
                    navigateToDetails(deviceId)
                })
            rv_favorites.layoutManager = LinearLayoutManager(context)
        }
    }

    private fun getFavoriteDevices(favoritesId : MutableList<String>){
        var index = 0
        favorites.clear()
        favoritesId.forEach {id ->
            favorites.add(index,
                devices.single {
                    it.id == id
                }
            )
            index++
        }
    }

    private fun favoriteAction(deviceId: String,position : Int){
        val newFavorites = (activity as MainActivity).getFavoritesId()
        if (newFavorites.contains(deviceId)){
            newFavorites.remove(deviceId)
            favorites.removeAt(position)
            rv_favorites.adapter?.apply {
                notifyItemRemoved(position)
                notifyItemRangeChanged(position,newFavorites.size)
            }
            (activity as MainActivity).setFavoritesId(newFavorites)
        }
    }

    private fun navigateToDetails(deviceId : String){
        val device : MutableList<DevicesResponse> = arrayListOf()
        device.add(0,
            devices.single {
                it.id == deviceId
            }
        )
        findNavController().navigate(FavoritesFragmentDirections.actionFavoriteToDeviceDetails(Devices(device)))
    }
}
