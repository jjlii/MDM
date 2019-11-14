package com.example.mdm_everis.device_details.reserve


import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.navArgs
import com.example.domain.devices.DevicesResponse
import com.example.mdm_everis.MainActivity
import com.example.mdm_everis.R
import com.example.mdm_everis.base.BaseFragment
import kotlinx.android.synthetic.main.reserve_process_fragment.*
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog
import java.util.*


class ReserveProcessFragment : BaseFragment<ReserveProcessViewModel>() , DatePickerDialog.OnDateSetListener{

    //******************************************* BaseFragment abstract ****************************

    override fun getLayout() = R.layout.reserve_process_fragment
    override fun getViewModel() = ReserveProcessViewModel::class

    //******************************************* End BaseFragment abstract ************************

    lateinit var datePickerDialog: DatePickerDialog
    private val args : ReserveProcessFragmentArgs by navArgs()
    var device : DevicesResponse? = null

    //*******************************************  Calendar ****************************************

    private val c = Calendar.getInstance()
    private val maxC = Calendar.getInstance()
    private val myYear = c.get(Calendar.YEAR)
    private val myMonth = c.get(Calendar.MONTH)
    private val myDay = c.get(Calendar.DAY_OF_MONTH)
    private var editTextClick = ""
    //*******************************************  End Calendar ************************************





    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showNavbar(false)
        device = args.device.allDevices[0]
        initListener()
    }

    //******************************************* Init *********************************************
    private fun initListener(){
        maxC.set(Calendar.MONTH, myMonth + 1)
        et_start_date.setOnClickListener {
            editTextClick = "start"
            setCalendar("Fecha inicio de reserva","StartDatePickerDialog")
        }
        et_end_date.setOnClickListener {
            editTextClick = "end"
            setCalendar("Fecha fin de reserva","EndDatePickerDialog")
        }

    }
    //******************************************* End Init *****************************************

    //******************************************* Observers ****************************************
    //******************************************* End Observers ************************************



    //******************************************* DatePicker ***************************************

    override fun onDateSet(view: DatePickerDialog?, year: Int, monthOfYear: Int, dayOfMonth: Int) {
        val date = changeFormat(year,monthOfYear,dayOfMonth)
        when(editTextClick){
            "start" -> et_start_date.setText(date)
            "end" -> et_end_date.setText(date)
        }

    }



    private fun setCalendar( title:String, tag : String){
        datePickerDialog = DatePickerDialog.newInstance(this,myYear,myMonth,myDay)
        datePickerDialog.isThemeDark = false
        datePickerDialog.setTitle(title)
        disableWeekend()
        datePickerDialog.maxDate = maxC
        datePickerDialog.minDate = c
        datePickerDialog.show((activity as MainActivity).fragmentManager,tag)

    }

    private fun changeFormat(year:Int,month:Int,day:Int): String{
        val realMonth = month + 1
        val sMonth = if (realMonth<10){
            "0$realMonth"
        }else{
            "$month"
        }
        val sDay= if (day<10){
            "0$day"
        }else{
            "$day"
        }
        return "$sDay/$sMonth/$year"
    }

    private fun disableWeekend(){
        var day : Calendar
        val weekends : MutableList<Calendar> = arrayListOf()
        val dayValue = myDay
        for (i  in 0 .. 31 ){
            day = Calendar.getInstance()
            day.set(Calendar.DAY_OF_MONTH, dayValue + i)
            if (day[Calendar.DAY_OF_WEEK] == Calendar.SATURDAY
                || day[Calendar.DAY_OF_WEEK] == Calendar.SUNDAY ){
                weekends.add(day)
            }
        }
        val disableDays : Array<Calendar> = weekends.toTypedArray()
        datePickerDialog.disabledDays = disableDays
    }


    //******************************************* End DatePicker ***********************************

}
