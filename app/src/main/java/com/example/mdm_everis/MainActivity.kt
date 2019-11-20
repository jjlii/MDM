package com.example.mdm_everis

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import androidx.navigation.findNavController
import com.example.domain.devices.DevicesResponse
import com.example.domain.reserves.ReserveResponse
import com.example.domain.user.UserResponse
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var bottomNavigationView: BottomNavigationView


    private var user : UserResponse = UserResponse("","","", arrayListOf())
    private var userReserves : List<ReserveResponse> = arrayListOf()
    private var devices : List<DevicesResponse> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bottomNavigationView = this.bottom_navigation
    }

    override fun onSupportNavigateUp() = findNavController( R.id.nav_host_fragment).navigateUp()


    fun getNavBar() : BottomNavigationView = bottomNavigationView

    fun getUser() = user

    fun setUser(newUser : UserResponse){
        user = newUser
    }

    fun getUserReserves() = userReserves

    fun setUserReserves(newReserves : List<ReserveResponse>){
        userReserves = newReserves
    }

    fun getDevice() = devices

    fun setDevice(newDevices : List<DevicesResponse>){
        devices = newDevices
    }



}
