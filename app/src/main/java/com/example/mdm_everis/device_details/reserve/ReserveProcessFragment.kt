package com.example.mdm_everis.device_details.reserve


import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.navArgs
import com.example.domain.devices.DevicesResponse
import com.example.domain.reserves.ReserveResponse
import com.example.mdm_everis.MainActivity
import com.example.mdm_everis.R
import com.example.mdm_everis.base.BaseFragment
import com.example.mdm_everis.splitWithSpaceAfter
import com.example.mdm_everis.splitWithSpaceBefore
import kotlinx.android.synthetic.main.reserve_process_fragment.*
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog
import java.text.SimpleDateFormat
import java.util.*


class ReserveProcessFragment : BaseFragment<ReserveProcessViewModel>() , DatePickerDialog.OnDateSetListener{

    //******************************************* BaseFragment abstract ****************************

    override fun getLayout() = R.layout.reserve_process_fragment
    override fun getViewModel() = ReserveProcessViewModel::class

    //******************************************* End BaseFragment abstract ************************

    lateinit var datePickerDialog: DatePickerDialog
    private val args : ReserveProcessFragmentArgs by navArgs()
    var device : DevicesResponse? = null
    private var deviceReserve : List<ReserveResponse> = arrayListOf()

    //*******************************************  Calendar ****************************************
    private val c = Calendar.getInstance()
    private var maxC = Calendar.getInstance()
    private var minC = Calendar.getInstance()
    private val myYear = c.get(Calendar.YEAR)
    private val myMonth = c.get(Calendar.MONTH)
    private val myDay = c.get(Calendar.DAY_OF_MONTH)
    private var editTextClick = ""
    private var disableDays : MutableList<Calendar> = arrayListOf()
    //*******************************************  End Calendar ************************************





    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showNavbar(false)
        device = args.device.allDevices[0]
        deviceReserve = args.reserves.allReserves
        initObserver()
        initListener()
    }

    //******************************************* Init *********************************************

    private fun initListener(){

        et_start_date.setOnClickListener {
            editTextClick = "start"
            setCalendar("Fecha inicio de reserva","StartDatePickerDialog")
        }
        et_end_date.setOnClickListener {
            editTextClick = "end"
            setCalendar("Fecha fin de reserva","EndDatePickerDialog")
        }
        btn_reserve.setOnClickListener {

        }

    }

    private fun initObserver(){
    }

    //******************************************* End Init *****************************************

    //******************************************* Observers ****************************************

    //******************************************* End Observers ************************************



    //******************************************* DatePicker ***************************************

    override fun onDateSet(view: DatePickerDialog?, year: Int, monthOfYear: Int, dayOfMonth: Int) {
        val date = changeFormat(year,monthOfYear,dayOfMonth)
        when(editTextClick){
            "start" -> {
                et_start_date.setText(date)
                ly_end_date.visibility = View.VISIBLE
                et_end_date.setText("")
            }
            "end" ->{
                et_end_date.setText(date)
                btn_reserve.visibility = View.VISIBLE
            }
        }
    }



    private fun setCalendar( title:String, tag : String){
        datePickerDialog = DatePickerDialog.newInstance(this,myYear,myMonth,myDay)
        datePickerDialog.isThemeDark = false
        datePickerDialog.setTitle(title)
        maxC = Calendar.getInstance()
        maxC.set(Calendar.MONTH, myMonth + 1)
        getReservedDays(tag)
        when(tag){
            "StartDatePickerDialog"-> {
                minC = Calendar.getInstance()
            }
            "EndDatePickerDialog"-> {
                var foundEndD = false
                val startD : String? = et_start_date.text?.substring(0..1)
                val startM : String? = et_start_date.text?.substring(3..4)
                val startY : String? = et_start_date.text?.substring(6..9)
                var endD : String
                var endM : String
                var endY : String
                var endDate: String
                val auxC = Calendar.getInstance()
                with(minC){
                    set(Calendar.DAY_OF_MONTH,startD!!.toInt()-1)
                    set(Calendar.MONTH,startM!!.toInt()-1)
                    set(Calendar.YEAR,startY!!.toInt())
                }
                deviceReserve.forEach {
                    endDate = convertLongToDate(it.startDate.toLong(),"dd/MM/yyyy")
                    endD = endDate.substring(0..1)
                    endM = endDate.substring(3..4)
                    endY = endDate.substring(6..9)
                    with(auxC){
                        set(Calendar.YEAR,endY.toInt())
                        set(Calendar.MONTH,endM.toInt()-1)
                        set(Calendar.DAY_OF_MONTH,endD.toInt())
                    }
                    if (minC<auxC && auxC<maxC){
                        with(maxC){
                            set(Calendar.YEAR,endY.toInt())
                            set(Calendar.MONTH,endM.toInt()-1)
                            set(Calendar.DAY_OF_MONTH,endD.toInt())
                        }
                        foundEndD = true
                    }
                }
                if (!foundEndD){
                    maxC = Calendar.getInstance()
                    maxC.set(Calendar.MONTH, myMonth + 1)
                }
            }
        }
        datePickerDialog.maxDate = maxC
        datePickerDialog.minDate = minC
        val days : Array<Calendar> = disableDays.toTypedArray()
        datePickerDialog.disabledDays = days
        datePickerDialog.show((activity as MainActivity).fragmentManager,tag)
    }

    private fun changeFormat(year:Int,month:Int,day:Int): String{
        val realMonth = month + 1
        val sMonth = if (realMonth<10){
            "0$realMonth"
        }else{
            "$realMonth"
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
        val dayValue = myDay
        val monthDay = 31
        for (i  in 0 .. monthDay ){
            day = Calendar.getInstance()
            day.set(Calendar.DAY_OF_MONTH, dayValue + i)
            if (day[Calendar.DAY_OF_WEEK] == Calendar.SATURDAY
                || day[Calendar.DAY_OF_WEEK] == Calendar.SUNDAY ){
                disableDays.add(day)
            }
        }
    }

    private fun getReservedDays(tag : String){
        disableDays = arrayListOf()
        disableWeekend()
        if (deviceReserve.isNotEmpty()){
            deviceReserve.forEach {reserve ->
                disableDays(convertLongToDate(reserve.startDate.toLong(),"dd/MM/yyyy HH:mm"),convertLongToDate(reserve.endDate.toLong(),"dd/MM/yyyy HH:mm"),tag)
            }
        }
    }

    private fun disableDays( startDate : String, endDate : String,tag : String){
        var day : Calendar
        var timeInMillis : Long
        val dayValue = myDay
        val monthDay = 31
        var found = false
        val startDay = stringDateToLong(startDate.splitWithSpaceBefore(),"dd/MM/yyyy")
        val endDay = stringDateToLong(endDate.splitWithSpaceBefore(),"dd/MM/yyyy")
        if (!checkEndHour(startDate,endDate)&& tag =="StartDatePickerDialog"){
            day = Calendar.getInstance()
            day.timeInMillis=startDay
            disableDays.add(day)
        }
        for (i in 0 .. monthDay){
            day = Calendar.getInstance()
            day = setMomentOfDay(day,dayValue + i)
            timeInMillis = day.timeInMillis
            if (timeInMillis == startDay && checkEnableStartDate(stringDateToLong(startDate,"dd/MM/yyyy HH:mm").toString()) ||
                timeInMillis == endDay && checkEnableEndDate(stringDateToLong(endDate,"dd/MM/yyyy HH:mm").toString())){
                if (tag =="StartDatePickerDialog"){
                    disableDays.add(day)
                }
            }
            if (timeInMillis > startDay || found){
                found =
                    if(timeInMillis< endDay){
                        disableDays.add(day)
                        true
                    }else{
                        false
                    }
            }
        }
    }

    private fun setMomentOfDay(day : Calendar , dayValue : Int ) =
        day.apply {
            set(Calendar.DAY_OF_MONTH, dayValue)
            set(Calendar.HOUR_OF_DAY,0)
            set(Calendar.MINUTE,0)
            set(Calendar.SECOND,0)
            set(Calendar.MILLISECOND,0)
        }

    private fun checkEnableStartDate(date : String) : Boolean =
        deviceReserve.filter {
            it.endDate == date
        }.any()

    private fun checkEnableEndDate(date : String) : Boolean =
        deviceReserve.filter {
            it.startDate == date
        }.any()

    private fun checkEndHour(startD: String,endD: String) : Boolean {
        val sd = startD.splitWithSpaceBefore()
        val sh = startD.splitWithSpaceAfter()
        val ed = endD.splitWithSpaceBefore()
        if (sh == "09:00"){
            if (sd == ed)
                return true
        }
        return false
    }
    //******************************************* End DatePicker ***********************************

    private fun stringDateToLong(strDate : String, format : String) : Long{
        val f = SimpleDateFormat(format, Locale.getDefault())
        val date = f.parse(strDate)
        return date.time
    }
    private fun convertLongToDate(date : Long,format : String) : String {
        val d = Date(date)
        val f = SimpleDateFormat(format,Locale.getDefault())
        return f.format(d)
    }

}
