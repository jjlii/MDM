package com.example.mdm_everis.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.devices.DevicesResponse
import com.example.mdm_everis.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.dispositivos_items.view.*

class DispositivosAdapter(private var devices : List<DevicesResponse>,private var isReservados : Boolean) : RecyclerView.Adapter<DispositivosAdapter.ViewHolder>(){

    private var mobile = ""
    private var so = ""


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.dispositivos_items,parent,false)
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
                tv_movil_content.text = mobile
                tv_so_content.text = so
                if(!isReservados){
                    tv_f_screen_size_content.text = devices.screenSize
                    tv_screen_r_content.text = devices.screenResolution
                }else{
                    tv_f_inicio.visibility = View.VISIBLE
                    tv_f_inicio_content.visibility = View.VISIBLE
                    tv_f_fin.visibility = View.VISIBLE
                    tv_f_inicio_content.visibility = View.VISIBLE
                }
                Picasso.get().load(devices.picture).into(iv_foto_dispositivo)
            }
        }
    }

}