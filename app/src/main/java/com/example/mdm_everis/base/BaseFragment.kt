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
import org.koin.android.viewmodel.ext.android.viewModelByClass
import kotlin.reflect.KClass

abstract class BaseFragment<VM : BaseViewModel> : Fragment() {

    protected val viewModel: VM by viewModelByClass(this.getViewModel())

    //******************************************* abstract variable ********************************

    abstract fun getLayout() : Int
    abstract fun getViewModel() : KClass<VM>

    //******************************************* End abstract variable ****************************

    val baseNavBar by lazy {
        (activity as MainActivity).getNavBar()
    }
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

    //******************************************* Loading Dialog ***********************************

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

    //******************************************* End Loading Dialog *******************************

    //******************************************* Nav Bar ******************************************

    fun showNavbar(show: Boolean){
        if (show){
            baseNavBar.visibility = View.VISIBLE
        }else{
            baseNavBar.visibility = View.GONE
        }
    }

    //******************************************* End Nav Bar **************************************

    fun toast( message: String, duration: Int = Toast.LENGTH_LONG)=
        Toast.makeText(context, message , duration).show()

}