package com.example.mdm_everis

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import com.example.core.Constant
import com.example.domain.devices.DevicesResponse
import com.example.domain.reserves.ReserveResponse
import com.example.domain.user.UserResponse
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    //private lateinit var firebaseAnalytics : FirebaseAnalytics
    lateinit var bottomNavigationView: BottomNavigationView
    private var fragment = ""
    private var user : UserResponse = UserResponse("","","", arrayListOf())
    private var userReserves : List<ReserveResponse> = arrayListOf()
    private var devices : List<DevicesResponse> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //firebaseAnalytics = FirebaseAnalytics.getInstance(this)
        bottomNavigationView = this.bottom_navigation
    }

    override fun onBackPressed() {
        if (fragment == Constant.FragmentFlag.RESERVES ||
                fragment == Constant.FragmentFlag.LOGIN){
            val intent = Intent(Intent.ACTION_MAIN)
            intent.addCategory(Intent.CATEGORY_HOME)
            startActivity(intent)
        }else{
            super.onBackPressed()
        }
    }

    override fun onSupportNavigateUp() = findNavController( R.id.nav_host_fragment).navigateUp()


    fun getNavBar() : BottomNavigationView = bottomNavigationView

    fun getFragment() = fragment

    fun setFragment(newFragment : String) {
        fragment = newFragment
    }

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
