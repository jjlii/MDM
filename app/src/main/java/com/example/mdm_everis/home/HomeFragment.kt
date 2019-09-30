package com.example.mdm_everis.home

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf

import com.example.mdm_everis.R
import com.example.mdm_everis.home.dispositivos.DispositivosFragment
import com.example.mdm_everis.home.mis_reservas.MisReservasFragment
import com.example.mdm_everis.home.reservas_caducadas.ReservasCaducadasFragment
import com.example.mdm_everis.navigateTo
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.home_fragment.*

class HomeFragment : Fragment() {

    companion object {
        fun setArguments() = bundleOf()
    }

    private lateinit var viewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.home_fragment, container, false)

        /*
        btn_navigation.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.nav_reservas ->{
                    navigateTo(R.id.reservas_screen,MisReservasFragment.setArguments())
                    true
                }
                R.id.nav_passed -> {
                    navigateTo(R.id.caducadas_screen,ReservasCaducadasFragment.setArguments())
                    true
                }
                R.id.nav_dispositivos->{
                    navigateTo(R.id.dispositivos_screen,DispositivosFragment.setArguments())
                    true
                }
                else -> false
            }
        }
         */

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
