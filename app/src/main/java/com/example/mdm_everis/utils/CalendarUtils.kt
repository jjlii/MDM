package com.example.mdm_everis.utils

import android.app.DatePickerDialog
import android.content.Context
import android.widget.DatePicker
import android.widget.EditText
import java.util.*
//import com.wdullaer.materialdatetimepicker.date.DatePickerDialog

object CalendarUtils {
    private val c: Calendar = Calendar.getInstance()
    private val year = c.get(Calendar.YEAR)
    private val month = c.get(Calendar.MONTH)
    private val day = c.get(Calendar.DAY_OF_MONTH)

    fun datePicker(context: Context, editText : EditText){
        val dpd= DatePickerDialog(context,
            DatePickerDialog.OnDateSetListener{ _: DatePicker, pickedYear:Int, pickedMonth:Int, pickedDay : Int ->
                editText.setText(changeFormat(pickedYear,pickedMonth,pickedDay))
            },year,month,day)
        dpd.datePicker.minDate = System.currentTimeMillis()
        dpd.show()
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

}