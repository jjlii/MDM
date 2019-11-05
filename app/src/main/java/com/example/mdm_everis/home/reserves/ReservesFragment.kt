package com.example.mdm_everis.home.reserves


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.domain.user.UserResponse
import com.example.mdm_everis.MainActivity

import com.example.mdm_everis.R
import com.example.mdm_everis.base.BaseFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class ReservesFragment : BaseFragment<ReservesViewModel>() {



    override fun getLayout() = R.layout.reserves_fragment
    override fun getViewModel() = ReservesViewModel::class
    override val showToolbar: Boolean = false

    private val args : ReservesFragmentArgs by navArgs()

    lateinit var userId : String
    lateinit var user : UserResponse

    private val navBar by lazy {
        (activity as MainActivity).getNavBar()
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showNavbar(true)
        navBar.menu.getItem(0).isChecked = true
        navBar.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        userId = args.userId
        initListener()
        initObservers()
        return inflater.inflate(R.layout.reserves_fragment, container, false)
    }

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { menuItem ->
        when (menuItem.itemId) {
            R.id.nav_reserves -> {
                findNavController().popBackStack(R.id.reserves_screen,false)
                return@OnNavigationItemSelectedListener true
            }
            R.id.nav_favourites -> {
                findNavController().popBackStack(R.id.reserves_screen,false)
                findNavController().navigate(ReservesFragmentDirections.actionToFavourites())
                return@OnNavigationItemSelectedListener true
            }
            R.id.nav_devices -> {
                findNavController().popBackStack(R.id.reserves_screen,false)
                findNavController().navigate(ReservesFragmentDirections.actionToDevices())
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

    private fun initObservers(){
        //viewModel.devicesLD.observe(this,reservasObserver)
        viewModel.getUserByIdLD.observe(this,getUserByIdObserver)
    }

    private fun initListener(){
        //viewModel.allDevies()
        viewModel.getUserById(userId)
    }

    private  val getUserByIdObserver = Observer<UserResponse>{
        it?.let {
            user = it
        } ?: run{
            toast("Se ha habido un error al obtener el usuario")
        }
    }




/*



    private val reservasObserver = Observer<List<DevicesResponse>>{
        if (it == null){
            toast("Error")
        }else{
            rv_reservas.adapter = ReservasAdapter(it)
            rv_reservas.layoutManager = LinearLayoutManager(context)
        }
    }

 */





}
