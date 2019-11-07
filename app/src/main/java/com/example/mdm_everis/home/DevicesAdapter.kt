package com.example.mdm_everis.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.core.Constant
import com.example.domain.devices.DevicesResponse
import com.example.mdm_everis.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.devices_items.view.*

class DevicesAdapter(private var devices : List<DevicesResponse>, private var flag : String) : RecyclerView.Adapter<DevicesAdapter.ViewHolder>(){

    private var mobile = ""
    private var so = ""


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.devices_items,parent,false)
    )
    override fun getItemCount()= devices.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(devices[position])
    }


    inner class ViewHolder(view : View) : RecyclerView.ViewHolder(view) {
        fun bind(devices : DevicesResponse){
            with(itemView){
                mobile = "${devices.brand} ${devices.model}"
                so = "${devices.so} ${devices.version}"
                tv_device_content.text = mobile
                tv_so_content.text = so
                when(flag){
                    Constant.AdapterFlag.RESERVES -> setVisibilityReserves(this)
                    Constant.AdapterFlag.FAVORITES -> {
                        setVisibilityNotReserves(this)
                        btn_favorite.isChecked = true
                        tv_screen_size_content.text = devices.screenSize
                        tv_screen_r_content.text = devices.screenResolution
                    }
                    Constant.AdapterFlag.DEVICES -> {
                        setVisibilityNotReserves(this)
                        btn_favorite.isChecked = false
                        tv_screen_size_content.text = devices.screenSize
                        tv_screen_r_content.text = devices.screenResolution
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
                tv_screen_size_content.visibility = g
                tv_screen_r.visibility = g
                tv_screen_r_content.visibility = g
                tv_f_start.visibility = v
                tv_f_start_content.visibility = v
                tv_f_end.visibility = v
                tv_f_start_content.visibility = v
            }
        }
        private fun setVisibilityNotReserves(view: View){
            val v = View.VISIBLE
            val g = View.GONE
            view.apply {
                tv_screen_size.visibility = v
                tv_screen_size_content.visibility = v
                tv_screen_r.visibility = v
                tv_screen_r_content.visibility = v
                tv_f_start.visibility = g
                tv_f_start_content.visibility = g
                tv_f_end.visibility = g
                tv_f_start_content.visibility = g
            }
        }

    }

}