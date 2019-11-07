package com.example.mdm_everis.home.reserves


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.domain.devices.DevicesResponse
import com.example.domain.user.UserResponse
import com.example.mdm_everis.Devices
import com.example.mdm_everis.Favorites
import com.example.mdm_everis.MainActivity

import com.example.mdm_everis.R
import com.example.mdm_everis.base.BaseFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

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
    private var favorites: MutableList<DevicesResponse> = arrayListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        user = args.user
        devices = args.devices
        getFavoriteDevices()
        initListener()
        initObservers()
        return inflater.inflate(R.layout.reserves_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showNavbar(true)
        navBar.menu.getItem(0).isChecked = true
        navBar.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

    }

    //******************************************* Init *********************************************

    private fun initObservers(){
    }

    private fun initListener(){
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
                findNavController().navigate(ReservesFragmentDirections.actionToFavorites(Devices(favorites)))
                return@OnNavigationItemSelectedListener true
            }
            R.id.nav_devices -> {
                findNavController().popBackStack(R.id.reserves_screen,false)
                findNavController().navigate(ReservesFragmentDirections.actionToDevices(devices))
                return@OnNavigationItemSelectedListener true
            }
            R.id.nav_profile -> {
                findNavController().popBackStack(R.id.reserves_screen,false)
                findNavController().navigate(ReservesFragmentDirections.actionToProfile(user))
                return@OnNavigationItemSelectedListener true
            }
        }
        false

    }

    //******************************************* EndBottom Nav Bar ********************************

    //******************************************* Observers ****************************************
    //******************************************* End Observers ************************************

    private fun getFavoriteDevices(){
        var index = 0
        user.favourites.forEach {id ->
            favorites.add(index,
                devices.allDevices.single {
                    it.id == id
                }
            )
            index++
        }
    }

}
