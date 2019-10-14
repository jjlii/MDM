package com.example.mdm_everis.base

import android.app.AlertDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.mdm_everis.MainActivity
import com.example.mdm_everis.R
import com.example.mdm_everis.hideKeyboard
import com.example.mdm_everis.home.HomeFragment
import com.example.mdm_everis.home.dispositivos.DispositivosFragment
import com.example.mdm_everis.home.reservas_caducadas.ReservasCaducadasFragment
import com.example.mdm_everis.navigateTo
import com.google.android.material.bottomnavigation.BottomNavigationView
import org.koin.android.viewmodel.ext.android.viewModelByClass
import kotlin.reflect.KClass

abstract class BaseFragment<VM : BaseViewModel> : Fragment() {

    protected val viewModel: VM by viewModelByClass(this.getViewModel())

    abstract fun getLayout(): Int
    abstract fun getViewModel(): KClass<VM>
    abstract val showToolbar: Boolean

    private lateinit var navbar : BottomNavigationView




    private var progressDialog: AlertDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.loadingMLD.observe(this, Observer {
            if (it) showLoading()
            else hideLoading()
        })
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(getLayout(),container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navbar = (activity as MainActivity).getNavBar()
        navbar.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }


    fun setToolbarTitle(screenTitle: String){
        (activity as MainActivity).setTitle(screenTitle)
    }

    private fun Fragment.createLoadingDialog(): AlertDialog?{
        return this.activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.setView(R.layout.fullscreen_loading_dialog)
            builder.setCancelable(false)
            builder.create().apply {
                window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            }
        }
    }



    private fun showLoading(){
        if (progressDialog == null){
            progressDialog = createLoadingDialog()
            progressDialog?.show()
            view?.hideKeyboard()
        }
    }


    private fun hideLoading(){
        progressDialog?.dismiss()
        progressDialog = null
    }



    fun toast( message: String, duration: Int = Toast.LENGTH_LONG)=
        Toast.makeText(context, message , duration).show()



    //Bottom navbar

    fun showNavbar(show: Boolean){
        if (show){
            navbar.visibility = View.VISIBLE
        }else{
            navbar.visibility = View.GONE
        }
    }

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { menuItem ->
        when (menuItem.itemId) {
            R.id.nav_reservas -> {
                navigateTo(R.id.home_screen,HomeFragment.setArguments())
                return@OnNavigationItemSelectedListener true
            }
            R.id.nav_passed -> {
                navigateTo(R.id.caducadas_screen,ReservasCaducadasFragment.setArguments())
                return@OnNavigationItemSelectedListener true
            }
            R.id.nav_dispositivos -> {
                navigateTo(R.id.dispositivos_screen,DispositivosFragment.setArguments())
                return@OnNavigationItemSelectedListener true
            }
        }
        false

    }

    //Top toolbar


}