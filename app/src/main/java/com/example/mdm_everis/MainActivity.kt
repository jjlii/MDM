package com.example.mdm_everis

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import androidx.navigation.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bottomNavigationView = this.bottom_navigation
    }

    override fun onSupportNavigateUp() = findNavController( R.id.nav_host_fragment).navigateUp()



    fun setTitle(title: String){ supportActionBar?.title = title }

    fun getNavBar() : BottomNavigationView = bottomNavigationView

}
