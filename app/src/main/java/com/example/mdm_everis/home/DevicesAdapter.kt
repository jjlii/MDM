package com.example.mdm_everis.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.core.Constant
import com.example.domain.devices.DevicesResponse
import com.example.domain.reserves.ReserveResponse
import com.example.mdm_everis.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.devices_items.view.*
import java.text.SimpleDateFormat
import java.util.*

class DevicesAdapter(private var devices : List<DevicesResponse>,
                     private var reserves : List<ReserveResponse>?,
                     private var flag : String,
                     private var favoritesId : MutableList<String>,
                     private val favoriteAction : (deviceId : String,position : Int)->Unit,
                     private val touchAction :(deviceId : String) -> Unit ) : RecyclerView.Adapter<DevicesAdapter.ViewHolder>(){

    private var mobile = ""
    private var so = ""




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.devices_items,parent,false)
    )
    override fun getItemCount()= devices.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(devices[position],position)
    }


    inner class ViewHolder(view : View) : RecyclerView.ViewHolder(view) {
        fun bind(devices : DevicesResponse,position: Int){
            with(itemView){
                mobile = "${devices.brand} ${devices.model}"
                so = "${devices.so} ${devices.version}"
                tv_device_c.text = mobile
                tv_so_c.text = so
                btn_favorite.isChecked = favoritesId.contains(devices.id)
                setOnClickListener {
                    touchAction(devices.id)
                }

                btn_favorite.setOnClickListener {
                    favoriteAction(devices.id,position)
                }
                when(flag){
                    Constant.AdapterFlag.RESERVES -> {
                        setVisibilityReserves(this)
                        setDataReserves(this,devices.id)
                    }
                    else -> {
                        setVisibilityNotReserves(this)
                        tv_screen_size_c.text = devices.screenSize
                        tv_screen_r_c.text = devices.screenResolution
                    }
                }
                Picasso.get().load(devices.picture).into(iv_img_device)
            }

        }



        private fun setVisibilityReserves(view: View){
            val v = View.VISIBLE
            val g = View.GONE
            view.apply {
                tv_screen_size.visibility = g
                tv_screen_size_c.visibility = g
                tv_screen_r.visibility = g
                tv_screen_r_c.visibility = g
                tv_f_start.visibility = v
                tv_f_start_content.visibility = v
                tv_f_end.visibility = v
                tv_f_end_content.visibility = v
            }
        }
        private fun setVisibilityNotReserves(view: View){
            val v = View.VISIBLE
            val g = View.GONE
            view.apply {
                tv_screen_size.visibility = v
                tv_screen_size_c.visibility = v
                tv_screen_r.visibility = v
                tv_screen_r_c.visibility = v
                tv_f_start.visibility = g
                tv_f_start_content.visibility = g
                tv_f_end.visibility = g
                tv_f_end_content.visibility = g
            }
        }

        private fun setDataReserves(view: View, deviceId : String){

            var startDate = ""
            var endDate = ""
            val reserve = reserves?.single{
                it.deviceId == deviceId
            }
            reserve?.let {
                startDate = convertLongToDate(it.startDate.toLong())
                endDate = convertLongToDate(it.endDate.toLong())
            }
            reserve?.let {
                with(view){
                    tv_f_start_content.text = startDate
                    tv_f_end_content.text = endDate
                }
            }
        }

        private fun convertLongToDate(time : Long) : String {
            val date = Date(time)
            val format = SimpleDateFormat("dd/MM/yyyy HH:mm",Locale.getDefault())
            return format.format(date)
        }
    }

}