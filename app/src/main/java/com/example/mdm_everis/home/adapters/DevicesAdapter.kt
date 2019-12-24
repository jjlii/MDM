package com.example.mdm_everis.home.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.core.Constant
import com.example.domain.devices.DevicesResponse
import com.example.domain.reserves.ReserveResponse
import com.example.mdm_everis.R
import com.example.mdm_everis.convertLongToDate
import com.example.mdm_everis.stringDateToLong
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.devices_items.view.*
import java.text.SimpleDateFormat
import java.util.*

class DevicesAdapter(private var devices : List<DevicesResponse>,
                     private var reserves : List<ReserveResponse>,
                     private var flag : String,
                     private var favoritesId : MutableList<String>,
                     private val favoriteAction : (deviceId : String,position : Int)->Unit,
                     private val reserveAction : (deviceId : String,startDate : String?)->Unit,
                     private val touchAction :(deviceId : String) -> Unit) : RecyclerView.Adapter<DevicesAdapter.ItemViewHolder>(){
    private var mobile = ""
    private var so = ""
    private var reservesCopy : MutableList<ReserveResponse> = arrayListOf()
    val v = View.VISIBLE
    val g = View.GONE


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : ItemViewHolder {
        return ItemViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.devices_items,parent,false))
    }
    override fun getItemCount()= devices.size

    override fun onBindViewHolder(holderItem: ItemViewHolder, position: Int) {
        holderItem.bind(devices[position],position)
    }

    override fun getItemId(position: Int): Long {
        return devices[position].id.hashCode().toLong()
    }

    inner class ItemViewHolder(view : View) : RecyclerView.ViewHolder(view) {
        fun bind(device : DevicesResponse, position: Int){
            with(itemView){
                mobile = "${device.brand} ${device.model}"
                so = "${device.so} ${device.version}"
                tv_device_c.text = mobile
                tv_so_c.text = so
                btn_favorite.isChecked = favoritesId.contains(device.id)
                setOnClickListener {
                    touchAction(device.id)
                }

                btn_favorite.setOnClickListener {
                    favoriteAction(device.id,position)
                }
                when(flag){
                    Constant.FragmentFlag.RESERVES -> {
                        if(reservesCopy.isEmpty()&&reserves.isNotEmpty()){
                            reservesCopy = reserves.toMutableList()
                        }
                        setVisibilityReserves(this)
                        setDataReserves(this,device.id)
                        btn_reserve.setOnClickListener {
                            val sD = tv_f_start_content.text.toString()
                                .stringDateToLong(Constant.DateFormat.DATE_WITH_TIME).toString()
                            reserveAction(device.id,sD)
                        }
                    }
                    else -> {
                        setVisibilityNotReserves(this)
                        btn_reserve.setOnClickListener {
                            reserveAction(device.id,null)
                        }
                        tv_screen_size_c.text = device.screenSize
                        tv_screen_r_c.text = device.screenResolution
                    }
                }
                Picasso.get().load(device.picture).
                    placeholder(R.drawable.ic_phone_android_black_24dp).
                    into(iv_img_device)
            }

        }



        private fun setVisibilityReserves(view: View){
            view.apply {
                tv_screen_size.visibility = g
                tv_screen_size_c.visibility = g
                tv_screen_r.visibility = g
                tv_screen_r_c.visibility = g
                tv_f_start.visibility = v
                tv_f_start_content.visibility = v
                tv_f_end.visibility = v
                tv_f_end_content.visibility = v
                btn_reserve.text = "CANCELAR"
            }
        }
        private fun setVisibilityNotReserves(view: View){
            view.apply {
                tv_screen_size.visibility = v
                tv_screen_size_c.visibility = v
                tv_screen_r.visibility = v
                tv_screen_r_c.visibility = v
                tv_f_start.visibility = g
                tv_f_start_content.visibility = g
                tv_f_end.visibility = g
                tv_f_end_content.visibility = g
                btn_reserve.text = "RESERVAR"
            }
        }

        private fun setDataReserves(view: View, deviceId : String){
            var startDate = ""
            var endDate = ""
            val r = reservesCopy.filter{
                it.deviceId == deviceId
            }
            val reserve : ReserveResponse
            if (r.isNotEmpty()){
                r.let {
                    reserve = it[0]

                    startDate = reserve.startDate.toLong()
                        .convertLongToDate(Constant.DateFormat.DATE_WITH_TIME)
                    endDate = reserve.endDate.toLong()
                        .convertLongToDate(Constant.DateFormat.DATE_WITH_TIME)
                    reservesCopy.remove(reserve)
                    with(view){
                        tv_f_start_content.text = startDate
                        tv_f_end_content.text = endDate
                    }
                }
            }
        }
    }

}
