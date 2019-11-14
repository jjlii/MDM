package com.example.mdm_everis.device_details.reserve


import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.domain.devices.DevicesResponse
import com.example.mdm_everis.R
import com.example.mdm_everis.base.BaseFragment
import com.example.mdm_everis.device_details.DeviceDetailsFragmentDirections
import com.example.mdm_everis.utils.CalendarUtils
import kotlinx.android.synthetic.main.device_details_fragment.*
import kotlinx.android.synthetic.main.reserve_process_fragment.*


class ReserveProcessFragment : BaseFragment<ReserveProcessViewModel>() {
    //******************************************* BaseFragment abstract ****************************

    override fun getLayout() = R.layout.reserve_process_fragment
    override fun getViewModel() = ReserveProcessViewModel::class

    //******************************************* End BaseFragment abstract ************************

    private val args : ReserveProcessFragmentArgs by navArgs()
    var device : DevicesResponse? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showNavbar(false)
        device = args.device.allDevices[0]
        initListener()
    }

    //******************************************* Init *********************************************
    private fun initListener(){
        setCalendar(et_start_date)
        setCalendar(et_end_date)
    }
    //******************************************* End Init *****************************************

    //******************************************* Observers ****************************************
    //******************************************* End Observers ************************************

    private fun setCalendar(editText : EditText){
        editText.keyListener = null
        editText.setOnClickListener {
            context?.let {
                CalendarUtils.datePicker(it,editText)
            }
        }
    }
}
