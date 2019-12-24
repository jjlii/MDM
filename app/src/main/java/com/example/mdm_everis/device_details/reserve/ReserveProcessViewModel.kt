package com.example.mdm_everis.device_details.reserve

import android.app.Application
import android.widget.EditText
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.core.Constant
import com.example.core.failure.Failure
import com.example.domain.devices.DevicesUseCase
import com.example.domain.reserves.*
import com.example.domain.sign_up.CreateUserUseCase
import com.example.domain.user.GetUserByIdUseCase
import com.example.mdm_everis.*
import com.example.mdm_everis.base.BaseViewModel
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog
import kotlinx.android.synthetic.main.reserve_process_fragment.*
import java.lang.Exception
import java.util.*

class ReserveProcessViewModel(application: Application,
                              getUserByIdUseCase: GetUserByIdUseCase,
                              devicesUseCase: DevicesUseCase,
                              userReservesUseCase: UserReservesUseCase,
                              deviceReservesUseCase: DeviceReservesUseCase,
                              createUserUseCase: CreateUserUseCase,
                              private val createReserveUseCase: CreateReserveUseCase)
    : BaseViewModel(application,getUserByIdUseCase,devicesUseCase,userReservesUseCase,deviceReservesUseCase,createUserUseCase) {

    //********************************** LiveData **************************************************

    private val createReserveMLD = MutableLiveData<ReserveResponse>()
    val createReserveLD : LiveData<ReserveResponse> = createReserveMLD

    private val createReserveFailureMLD = MutableLiveData<Failure>()
    val createReserveFailure : LiveData<Failure> = createReserveFailureMLD
    var deviceReserve : List<ReserveResponse> = arrayListOf()
    //********************************** End LiveData **********************************************

    lateinit var datePickerDialog: DatePickerDialog
    private val c = Calendar.getInstance()
    private var maxC = Calendar.getInstance()
    private var minC = Calendar.getInstance()
    private val myYear = c.get(Calendar.YEAR)
    private val myMonth = c.get(Calendar.MONTH)
    private val myDay = c.get(Calendar.DAY_OF_MONTH)
    private var disableDays : MutableList<Calendar> = arrayListOf()
    private var  startD : String? =null
    var editTextClick = ""
    var  startM : String? = null
    var startY : String? = null

    //********************************** Create reserve ********************************************

    fun createNewReserve(reserve : ReserveResponse,deviceId : String){
        loadingMLD.value = true
        createReserveUseCase(CreateReserveReq(reserve,deviceId)){
            it.fold(
                ::createReserveFailure,
                ::createReserveSuccess
            )
        }
    }

    private fun createReserveFailure(failure: Failure){
        loadingMLD.value = false
        createReserveFailureMLD.value = failure
    }

    private fun createReserveSuccess(reserveResponse: ReserveResponse?) {
        createReserveMLD.value = reserveResponse
    }


    //********************************** End Create reserve ****************************************

    fun setCalendar( title:String, tag : String,fragment: ReserveProcessFragment,
                     et_start_date : EditText) : DatePickerDialog{
        datePickerDialog = DatePickerDialog.newInstance(fragment,myYear,myMonth,myDay)
        datePickerDialog.isThemeDark = false
        datePickerDialog.setTitle(title)
        maxC = Calendar.getInstance()

        when(tag){
            "StartDatePickerDialog"-> {
                minC= Calendar.getInstance()
                val strMinC = getMinCStartDate(deviceReserve)
                if (strMinC != "No encontrado"){
                    val longMinC = strMinC.stringDateToLong(Constant.DateFormat.DATE_WITHOUT_TIME)
                    val dateMinC = longMinC.convertLongToDate(Constant.DateFormat.DATE_WITHOUT_TIME)
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
                    set(Calendar.DAY_OF_MONTH,startD!!.toInt())
                    set(Calendar.MONTH,startM!!.toInt()-1)
                    set(Calendar.YEAR,startY!!.toInt())
                }
                with(maxC){
                    if(startM!!.toInt() == 12){
                        set(Calendar.DAY_OF_MONTH,0)
                    }else{
                        set(Calendar.DAY_OF_MONTH,minC[Calendar.MONTH]+1)
                    }
                    set(Calendar.MONTH,startM!!.toInt())
                    set(Calendar.YEAR,startY!!.toInt())
                }
                deviceReserve.forEach {
                    endDate = it.startDate.toLong().convertLongToDate(Constant.DateFormat.DATE_WITHOUT_TIME)
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
        getReservedDays(tag,deviceReserve)
        datePickerDialog.maxDate = maxC
        datePickerDialog.minDate = minC
        val days : Array<Calendar> = disableDays.toTypedArray()
        datePickerDialog.disabledDays = days
        return datePickerDialog
        //datePickerDialog.show((activity as MainActivity).fragmentManager,tag)
    }

    private fun getMinCStartDate(deviceReserve : List<ReserveResponse>) : String{
        val currentMoment = Calendar.getInstance().timeInMillis

        val currentDate = currentMoment.convertLongToDate(Constant.DateFormat.DATE_WITHOUT_TIME)

        val needDate = "$currentDate 09:00".stringDateToLong(Constant.DateFormat.DATE_WITH_TIME)
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
                    res = lateEndDate.convertLongToDate(Constant.DateFormat.DATE_WITHOUT_TIME)
                }else{
                    lateEndDate = filter[0].endDate.toLong()
                }
            }
            return res
        }
    }

    private fun getReservedDays(tag : String,deviceReserve : List<ReserveResponse>){
        disableDays = arrayListOf()
        disableWeekend(tag)
        if (deviceReserve.isNotEmpty()){
            deviceReserve.forEach {reserve ->
                disableDays(reserve.startDate.toLong().convertLongToDate(Constant.DateFormat.DATE_WITH_TIME),
                    reserve.endDate.toLong().convertLongToDate(Constant.DateFormat.DATE_WITH_TIME),tag)
            }
        }
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

    fun checkEndHour(startD: String,endD : String) : Boolean{
        val sd = startD.splitWithSpaceBefore()
        val sh = startD.splitWithSpaceAfter()
        val ed = endD.splitWithSpaceBefore()
        val eh = endD.splitWithSpaceAfter()
        if(sd == ed){
            if (sh == "09:00" && eh == "18:00"){
                return false
            }
        }
        return true
    }

    private fun disableDays( startDate : String, endDate : String,tag : String){
        var day : Calendar
        var timeInMillis : Long
        val dayValue = myDay
        val limit = 730
        var found = false
        val startDay = startDate.splitWithSpaceBefore().stringDateToLong(Constant.DateFormat.DATE_WITHOUT_TIME)
        val endDay = endDate.splitWithSpaceBefore().stringDateToLong(Constant.DateFormat.DATE_WITHOUT_TIME)
        if (!checkEndHour(startDate,endDate)&& tag =="StartDatePickerDialog"){
            day = Calendar.getInstance()
            day.timeInMillis=startDay
            disableDays.add(day)
        }
        for (i in 0 .. limit){
            day = Calendar.getInstance()
            day = setMomentOfDay(day,dayValue + i)
            timeInMillis = day.timeInMillis
            if (timeInMillis == startDay && checkEnableStartDate(startDate.stringDateToLong(Constant.DateFormat.DATE_WITH_TIME).toString()) ||
                timeInMillis == endDay && checkEnableEndDate(endDate.stringDateToLong(Constant.DateFormat.DATE_WITH_TIME).toString())){
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

    private fun checkEnableStartDate(date : String) : Boolean =
        deviceReserve.filter {
            it.endDate == date
        }.any()

    private fun checkEnableEndDate(date : String) : Boolean =
        deviceReserve.filter {
            it.startDate == date
        }.any()

    private fun setMomentOfDay(day : Calendar , dayValue : Int ) =
        day.apply {
            set(Calendar.DAY_OF_MONTH, dayValue)
            set(Calendar.HOUR_OF_DAY,0)
            set(Calendar.MINUTE,0)
            set(Calendar.SECOND,0)
            set(Calendar.MILLISECOND,0)
        }
}
