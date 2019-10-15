package com.example.mdm_everis.home.mis_reservas

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.Devices
import com.example.mdm_everis.R

class ReservasAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var devices  = listOf<Devices>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)=
        ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.reservados_items,parent,false)
        )


    override fun getItemCount() = devices.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

    }

    inner class ViewHolder(view : View) : RecyclerView.ViewHolder(view){
        fun bind(){
            with(itemView){
            }
        }
    }


}