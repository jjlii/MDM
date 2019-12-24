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
import com.example.mdm_everis.*
import com.example.mdm_everis.base.BaseFragment
import com.example.mdm_everis.utils.notification.NotificationUtils
import kotlinx.android.synthetic.main.reserve_process_fragment.*
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog
import java.lang.Exception
import java.util.*


class ReserveProcessFragment : BaseFragment<ReserveProcessViewModel>() , DatePickerDialog.OnDateSetListener{

    //******************************************* BaseFragment abstract ****************************

    override fun getLayout() = R.layout.reserve_process_fragment
    override fun getViewModel() = ReserveProcessViewModel::class

    //******************************************* End BaseFragment abstract ************************


    private val args : ReserveProcessFragmentArgs by navArgs()
    lateinit var device : DevicesResponse
    lateinit var user : UserResponse
    lateinit var newReserve : ReserveResponse

    //*******************************************  Calendar ****************************************
    //*******************************************  End Calendar ************************************





    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as MainActivity).setFragment(Constant.FragmentFlag.RESERVE_PROCESS)
        user = (activity as MainActivity).getUser()
        viewModel.fragmentFlag = Constant.FragmentFlag.RESERVE_PROCESS
        showNavbar(false)
        device = args.device.allDevices[0]
        viewModel.deviceReserve = args.reserves.allReserves
        initObserver()
        initListener()
    }

    //******************************************* Init *********************************************

    private fun initListener(){
        var startDate : Long
        var endDate : Long
        et_start_date.setOnClickListener {
            viewModel.editTextClick = "start"
            viewModel.setCalendar(title = "Fecha inicio de reserva",tag = "StartDatePickerDialog",fragment = this,et_start_date = et_start_date)
                .show((activity as MainActivity).fragmentManager,tag)
        }
        et_end_date.setOnClickListener {
            viewModel.editTextClick = "end"
            viewModel.setCalendar(title = "Fecha fin de reserva",tag = "EndDatePickerDialog",fragment = this,et_start_date = et_start_date)
                .show((activity as MainActivity).fragmentManager,tag)
        }
        btn_reserve.setOnClickListener {
            startDate = et_start_date.text.toString().stringDateToLong(Constant.DateFormat.DATE_WITHOUT_TIME)
            endDate = et_end_date.text.toString().stringDateToLong(Constant.DateFormat.DATE_WITHOUT_TIME)
            endDate += if(startDate == endDate){
                Constant.Hours.SIX
            }else{
                Constant.Hours.NINE
            }
            startDate += Constant.Hours.NINE
            newReserve = ReserveResponse("",user.id,startDate.toString(),endDate.toString(),device.id)
            viewModel.createNewReserve(newReserve,device.id)
            NotificationUtils().setNotification(endDate-1800000, (activity as MainActivity))

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
        viewModel.deviceReserve = it
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
        when(viewModel.editTextClick){
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
    //******************************************* End DatePicker ***********************************


}
