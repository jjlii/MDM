package com.example.mdm_everis.device_details.reserve


import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.core.Constant
import com.example.core.failure.Failure
import com.example.core.failure.ReserveFailure
import com.example.domain.devices.DevicesResponse
import com.example.domain.reserves.ReserveResponse
import com.example.domain.user.UserResponse
import com.example.mdm_everis.MainActivity
import com.example.mdm_everis.R
import com.example.mdm_everis.base.BaseFragment
import com.example.mdm_everis.splitWithSpaceAfter
import com.example.mdm_everis.splitWithSpaceBefore
import kotlinx.android.synthetic.main.reserve_process_fragment.*
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog
import java.lang.Exception
import java.util.*


class ReserveProcessFragment : BaseFragment<ReserveProcessViewModel>() , DatePickerDialog.OnDateSetListener{

    //******************************************* BaseFragment abstract ****************************

    override fun getLayout() = R.layout.reserve_process_fragment
    override fun getViewModel() = ReserveProcessViewModel::class

    //******************************************* End BaseFragment abstract ************************

    lateinit var datePickerDialog: DatePickerDialog
    private val args : ReserveProcessFragmentArgs by navArgs()
    lateinit var device : DevicesResponse
    private var deviceReserve : List<ReserveResponse> = arrayListOf()
    lateinit var user : UserResponse
    lateinit var newReserve : ReserveResponse

    //*******************************************  Calendar ****************************************
    private val c = Calendar.getInstance()
    private var maxC = Calendar.getInstance()
    private var minC = Calendar.getInstance()
    private val myYear = c.get(Calendar.YEAR)
    private val myMonth = c.get(Calendar.MONTH)
    private val myDay = c.get(Calendar.DAY_OF_MONTH)
    private var editTextClick = ""
    private var disableDays : MutableList<Calendar> = arrayListOf()
    private var  startD : String? =null
    var  startM : String? = null
    var startY : String? = null
    //*******************************************  End Calendar ************************************





    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as MainActivity).setFragment(Constant.FragmentFlag.RESERVE_PROCESS)
        user = (activity as MainActivity).getUser()
        viewModel.fragmentFlag = Constant.FragmentFlag.RESERVE_PROCESS
        showNavbar(false)
        device = args.device.allDevices[0]
        deviceReserve = args.reserves.allReserves
        initObserver()
        initListener()
    }

    //******************************************* Init *********************************************

    private fun initListener(){
        var startDate : Long
        var endDate : Long
        et_start_date.setOnClickListener {
            editTextClick = "start"
            setCalendar("Fecha inicio de reserva","StartDatePickerDialog")
        }
        et_end_date.setOnClickListener {
            editTextClick = "end"
            setCalendar("Fecha fin de reserva","EndDatePickerDialog")
        }
        btn_reserve.setOnClickListener {
            startDate = stringDateToLong(et_start_date.text.toString(),Constant.DateFormat.DATE_WITHOUT_TIME)+Constant.Hours.NINE
            endDate = stringDateToLong(et_end_date.text.toString(),Constant.DateFormat.DATE_WITHOUT_TIME) + Constant.Hours.NINE
            newReserve = ReserveResponse("",user.id,startDate.toString(),endDate.toString(),device.id)
            viewModel.createNewReserve(newReserve,device.id)
        }

    }

    private fun initObserver(){
        viewModel.deviceReservesLD.observe(this,deviceReservesObserver)
        viewModel.failureLD.observe(this,failureObserver)
        viewModel.createReserveLD.observe(this,createReserveObserver)
        viewModel.createReserveFailure.observe(this,createReserveFailureObserver)
        viewModel.userReservesLD.observe(this,getUserReserveObserver)
    }

    //******************************************* End Init *****************************************

    //******************************************* Observers ****************************************

    private val deviceReservesObserver = Observer<List<ReserveResponse>>{
        deviceReserve = it
    }

    private val failureObserver = Observer<Failure>{
        toast(it.toString())
    }

    private val createReserveObserver = Observer<ReserveResponse>{
        viewModel.getUserReserves(user.id)
    }

    private val createReserveFailureObserver = Observer<Failure>{
        val alertDialog = AlertDialog.Builder(context)
        et_start_date.setText("")
        et_end_date.setText("")
        et_end_date.visibility = View.GONE
        btn_reserve.visibility = View.GONE
        if (it == ReserveFailure.InvalidReserve ){
            alertDialog.setTitle("Fecha no disponible")
            alertDialog.setMessage("UPSS! Has sido muy lento alguien reservado esa fecha. ")
            alertDialog.setPositiveButton("Aceptar"
            ) { _, _ ->
                viewModel.deviceReserves(device.id)
            }
            alertDialog.show()
        }else{
            toast("No se ha podido crear la reserva")
        }
    }

    private val getUserReserveObserver = Observer<List<ReserveResponse>?>{
        it?.let {
            (activity as MainActivity).setUserReserves(it)
        }
        val alertDialog = AlertDialog.Builder(context)
        alertDialog.setTitle("Confirmación de la reserva")
        alertDialog.setMessage("Ha reservado el dispositivo desde " + et_start_date.text.toString()
                + " hasta " + et_end_date.text.toString()
                +". Recuerde hacer un uso responsable del dispositivo y devolverlo en la fecha establecida.")
        alertDialog.setPositiveButton("Aceptar"
        ) { _, _ ->
            findNavController().navigate(ReserveProcessFragmentDirections.actionReserveProcessToReserves(user.id))
        }
        alertDialog.show()

    }

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

        when(tag){
            "StartDatePickerDialog"-> {
                minC= Calendar.getInstance()
                val strMinC = getMinCStartDate()
                if (strMinC != "No encontrado"){
                    val longMinC = stringDateToLong(strMinC,Constant.DateFormat.DATE_WITHOUT_TIME)
                    val dateMinC = convertLongToDate(longMinC,Constant.DateFormat.DATE_WITHOUT_TIME)
                    with(minC){
                        set(Calendar.DAY_OF_MONTH,dateMinC.substring(0..1).toInt())
                        set(Calendar.MONTH,dateMinC.substring(3..4).toInt()-1)
                        set(Calendar.YEAR,dateMinC.substring(6..9).toInt())
                    }
                }
                with(maxC){
                    set(Calendar.DAY_OF_MONTH,minC[Calendar.DAY_OF_MONTH])
                    set(Calendar.MONTH,minC[Calendar.MONTH])
                    set(Calendar.YEAR,minC[Calendar.YEAR]+2)
                }
            }
            "EndDatePickerDialog"-> {
                var foundEndD = false
                startD = et_start_date.text?.substring(0..1)
                startM = et_start_date.text?.substring(3..4)
                startY = et_start_date.text?.substring(6..9)
                var endD : String
                var endM : String
                var endY : String
                var endDate: String
                val auxC = Calendar.getInstance()
                with(minC){
                    set(Calendar.DAY_OF_MONTH,startD!!.toInt()+1)
                    set(Calendar.MONTH,startM!!.toInt()-1)
                    set(Calendar.YEAR,startY!!.toInt())

                }
                deviceReserve.forEach {
                    endDate = convertLongToDate(it.startDate.toLong(),Constant.DateFormat.DATE_WITHOUT_TIME)
                    endD = endDate.substring(0..1)
                    endM = endDate.substring(3..4)
                    endY = endDate.substring(6..9)
                    with(auxC){
                        set(Calendar.MONTH,endM.toInt()-1)
                        set(Calendar.YEAR,endY.toInt())
                        set(Calendar.DAY_OF_MONTH,endD.toInt())
                    }
                    if (minC<auxC && auxC<maxC){
                        with(maxC){
                            set(Calendar.MONTH,endM.toInt()-1)
                            set(Calendar.YEAR,endY.toInt())
                            set(Calendar.DAY_OF_MONTH,endD.toInt())
                        }
                        foundEndD = true
                    }
                }
                if (!foundEndD){
                    maxC = Calendar.getInstance()
                    maxC.set(Calendar.DAY_OF_MONTH,minC[Calendar.DAY_OF_MONTH])
                    when(minC[Calendar.MONTH]){
                        Calendar.DECEMBER-> {
                            maxC.set(Calendar.MONTH, Calendar.JANUARY)
                            maxC.set(Calendar.YEAR,minC[Calendar.YEAR] + 1)
                        }
                        else -> {
                            maxC.set(Calendar.MONTH, minC[Calendar.MONTH] + 1)
                            maxC.set(Calendar.YEAR,minC[Calendar.YEAR])
                        }
                    }
                }
            }
        }
        getReservedDays(tag)
        datePickerDialog.maxDate = maxC
        datePickerDialog.minDate = minC
        val days : Array<Calendar> = disableDays.toTypedArray()
        datePickerDialog.disabledDays = days
        try {
            datePickerDialog.show((activity as MainActivity).fragmentManager,tag)
        }catch (error : Exception){
            toast("No hay reservas disponibles")
        }
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

    private fun disableWeekend(tag: String){
        var day : Calendar
        val dayValue = myDay
        var i = 0
        day = Calendar.getInstance()
        if (tag=="EndDatePickerDialog"){
            with(day){
                set(Calendar.DAY_OF_MONTH,minC[Calendar.DAY_OF_MONTH]+1)
                set(Calendar.MONTH,minC[Calendar.MONTH])
                set(Calendar.YEAR,minC[Calendar.YEAR])
            }
        }
        while (day.timeInMillis<maxC.timeInMillis){
            day = Calendar.getInstance()
            day.set(Calendar.DAY_OF_MONTH, dayValue + i)
            i++
            if (day[Calendar.DAY_OF_WEEK] == Calendar.SATURDAY
                || day[Calendar.DAY_OF_WEEK] == Calendar.SUNDAY ){
                disableDays.add(day)
            }
        }
    }

    private fun getReservedDays(tag : String){
        disableDays = arrayListOf()
        disableWeekend(tag)
        if (deviceReserve.isNotEmpty()){
            deviceReserve.forEach {reserve ->
                disableDays(convertLongToDate(reserve.startDate.toLong(),Constant.DateFormat.DATE_WITH_TIME),
                    convertLongToDate(reserve.endDate.toLong(),Constant.DateFormat.DATE_WITH_TIME),tag)
            }
        }
    }

    private fun disableDays( startDate : String, endDate : String,tag : String){
        var day : Calendar
        var timeInMillis : Long
        val dayValue = myDay
        val limit = 360
        var found = false
        val startDay = stringDateToLong(startDate.splitWithSpaceBefore(),Constant.DateFormat.DATE_WITHOUT_TIME)
        val endDay = stringDateToLong(endDate.splitWithSpaceBefore(),Constant.DateFormat.DATE_WITHOUT_TIME)
        if (!checkEndHour(startDate,endDate)&& tag =="StartDatePickerDialog"){
            day = Calendar.getInstance()
            day.timeInMillis=startDay
            disableDays.add(day)
        }
        for (i in 0 .. limit){
            day = Calendar.getInstance()
            day = setMomentOfDay(day,dayValue + i)
            timeInMillis = day.timeInMillis
            if (timeInMillis == startDay && checkEnableStartDate(stringDateToLong(startDate,Constant.DateFormat.DATE_WITH_TIME).toString()) ||
                timeInMillis == endDay && checkEnableEndDate(stringDateToLong(endDate,Constant.DateFormat.DATE_WITH_TIME).toString())){
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

    private fun getMinCStartDate() : String{
        val currentMoment = Calendar.getInstance().timeInMillis
        val currentDate = convertLongToDate(currentMoment,Constant.DateFormat.DATE_WITHOUT_TIME)
        val needDate = stringDateToLong("$currentDate 09:00",Constant.DateFormat.DATE_WITH_TIME)
        val earlyReserve =deviceReserve.filter {
            it.startDate.toLong()<=needDate
        }
        if(earlyReserve.isEmpty()){
            return "No encontrado"
        }else{
            var res : String = ""
            var lateEndDate = 0L
            earlyReserve.forEach {
                if (it.endDate.toLong()>=lateEndDate){
                    lateEndDate = it.endDate.toLong()
                }
            }
            while (res == ""){
                val filter  = deviceReserve.filter {
                    it.startDate.toLong() == lateEndDate
                }
                if (filter.isEmpty()){
                    res = convertLongToDate(lateEndDate,Constant.DateFormat.DATE_WITHOUT_TIME)
                }else{
                    lateEndDate = filter[0].endDate.toLong()
                }
            }
            return res
        }
    }
    //******************************************* End DatePicker ***********************************


}
