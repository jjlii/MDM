package com.example.mdm_everis.home.favorites


import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.core.Constant
import com.example.domain.devices.DevicesResponse

import com.example.mdm_everis.R
import com.example.mdm_everis.base.BaseFragment
import com.example.mdm_everis.home.DevicesAdapter
import kotlinx.android.synthetic.main.favorites_fragment.*

class FavoritesFragment :BaseFragment<FavoritesViewModel>() {

    //******************************************* BaseFragment abstract ****************************

    override fun getLayout() = R.layout.favorites_fragment
    override fun getViewModel() = FavoritesViewModel::class

    //******************************************* End BaseFragment abstract ************************
    private val args : FavoritesFragmentArgs by navArgs()
    lateinit var favorites : List<DevicesResponse>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showNavbar(true)
        baseNavBar.menu.getItem(1).isChecked = true
        favorites = args.favoriteDevices.allDevices
        showAdapter()
        //favorites = args.favorites.fav
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
        if (favorites.isNotEmpty()){
            rv_favorites.adapter = DevicesAdapter(favorites,Constant.AdapterFlag.FAVORITES)
            rv_favorites.layoutManager = LinearLayoutManager(context)
        }
    }
}
