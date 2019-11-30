package com.example.mdm_everis.home.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.core.Constant
import com.example.domain.devices.DevicesResponse
import com.example.mdm_everis.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.category_item.view.*
import kotlinx.android.synthetic.main.devices_items.view.*

class DevicesCategoryAdapter(private var devices : List<DevicesResponse>,
                             private var favoritesId : MutableList<String>,
                             private val favoriteAction : (deviceId : String,position : Int)->Unit,
                             private val reserveAction : (deviceId : String,startDate : String?)->Unit,
                             private val touchAction :(deviceId : String) -> Unit): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var mobile = ""
    private var so = ""
    val v = View.VISIBLE
    val g = View.GONE

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType){
            CellType.HEADER.ordinal ->
                HeaderViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.category_item,parent,false))
            else->
                ItemViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.devices_items,parent,false))

        }
    }

    override fun getItemCount() = devices.size +1

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(getItemViewType(position)){
            CellType.HEADER.ordinal -> {
                val headerViewHolder = holder as HeaderViewHolder
                headerViewHolder.bind()
            }
            CellType.CONTENT.ordinal -> {
                val itemViewHolder = holder as ItemViewHolder
                itemViewHolder.bind(devices[position - 1],position-1)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when(position){
            0-> CellType.HEADER.ordinal
            else-> CellType.CONTENT.ordinal
        }
    }

    inner class HeaderViewHolder(itemView : View ) : RecyclerView.ViewHolder(itemView){
        fun bind(){
            with(itemView){
                Picasso.get().load(Constant.CategoryPhoto.ANDROID).
                    into(iv_android)
                Picasso.get().load(Constant.CategoryPhoto.IOS).
                    into(iv_ios)
                Picasso.get().load(Constant.CategoryPhoto.PHONE).
                    into(iv_phone)
                Picasso.get().load(Constant.CategoryPhoto.TABLET).
                    into(iv_tablet)
            }
        }
    }

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        fun bind(device : DevicesResponse,position: Int){
            with(itemView){
                setVisibility(this)
                setData(this,device)
                setOnClickListener {
                    touchAction(device.id)
                }
                btn_favorite.setOnClickListener {
                    favoriteAction(device.id,position)
                }
                btn_reserve.setOnClickListener {
                    reserveAction(device.id,null)
                }

            }
        }

        private fun setData(view :View,device : DevicesResponse){
            with(view){
                mobile = "${device.brand} ${device.model}"
                so = "${device.so} ${device.version}"
                tv_device_c.text = mobile
                tv_so_c.text = so
                btn_favorite.isChecked = favoritesId.contains(device.id)
                tv_screen_size_c.text = device.screenSize
                tv_screen_r_c.text = device.screenResolution
                Picasso.get().load(device.picture).
                    placeholder(R.drawable.ic_phone_android_black_24dp).
                    into(iv_img_device)
            }
        }
        private fun setVisibility(view : View){
            with(view){
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
    }

    enum class CellType(viewType: Int){
        HEADER(0),
        CONTENT(1)
    }

}