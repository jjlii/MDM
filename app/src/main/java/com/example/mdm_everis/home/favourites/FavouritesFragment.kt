package com.example.mdm_everis.home.favourites


import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf

import com.example.mdm_everis.R
import com.example.mdm_everis.base.BaseFragment

class FavouritesFragment :BaseFragment<FavouritesViewModel>() {

    //******************************************* BaseFragment abstract ****************************

    override fun getLayout() = R.layout.favourites_fragment
    override fun getViewModel() = FavouritesViewModel::class

    //******************************************* End BaseFragment abstract ************************

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showNavbar(true)
        baseNavBar.menu.getItem(1).isChecked = true
    }

    //******************************************* Init *********************************************
    //******************************************* End Init *****************************************

    //******************************************* Observers ****************************************
    //******************************************* End Observers ************************************

}
