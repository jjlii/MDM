package com.example.mdm_everis.base

import android.app.AlertDialog
import android.content.Context
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
import org.koin.android.viewmodel.ext.android.viewModelByClass
import kotlin.reflect.KClass

abstract class BaseFragment<VM : BaseViewModel> : Fragment() {

    protected val viewModel: VM by viewModelByClass(this.getViewModel())

    abstract fun getLayout(): Int
    abstract fun getViewModel(): KClass<VM>
    abstract val showToolbar: Boolean

    private var nfcDialog : AlertDialog? = null
    private var progressDialog: AlertDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.loadingMLD.observe(this, Observer {
            showLoading ->
            if (showLoading) showLoading()
            else hideLoading()
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as MainActivity).showToolbar(showToolbar)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(getLayout(),container,false)
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

    private fun Fragment.createNFCDialog(): AlertDialog?{
        return this.activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.setView(R.layout.fullscrren_nfc_dialog)
            builder.setCancelable(true)
            builder.create().apply {
                window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            }
        }
    }


    private fun showLoading(){
        if (progressDialog == null){
            progressDialog = createLoadingDialog()
            progressDialog?.show()
        }
    }

    private fun hideLoading(){
        progressDialog?.dismiss()
        progressDialog = null
    }

    private fun showNFCDialog(){
        if (nfcDialog==null){
            nfcDialog = createNFCDialog()
            nfcDialog?.show()
        }
    }

    private fun hideNFCDialog(){
        nfcDialog?.dismiss()
        nfcDialog = null
    }

    fun Context.toast(context: Context = applicationContext, message: String, duration: Int = Toast.LENGTH_LONG)=
        Toast.makeText(context, message , duration).show()
}