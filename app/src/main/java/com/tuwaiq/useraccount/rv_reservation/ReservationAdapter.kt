package com.tuwaiq.useraccount.rv_reservation

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.tuwaiq.useraccount.R

class ReservationAdapter(private val reservationList: MutableList<ReservationData>,
): RecyclerView.Adapter<ReservationHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReservationHolder {
        // inflate layout
        val view = LayoutInflater.from(parent.context).inflate(R.layout.reservation_rv_list,parent,false)
        return ReservationHolder(view)
    }

    override fun onBindViewHolder(holder: ReservationHolder, position: Int) {
        val reserve = reservationList.toList()[position]
        val splitTime= reserve.date.split("-")
        val getTime = "${splitTime[1]}/${splitTime[2]} - ${splitTime[3]}:${splitTime[4]}"
        holder.storeNameReservation.text = reserve.storeName
        holder.branchNameReservation.text = reserve.branchName
        holder.loc = reserve.maps
        holder.time.text = getTime
        holder.location.setOnClickListener {
            val gmmIntentUri = Uri.parse("google.navigation:q=${reserve.maps}")
            val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
            mapIntent.setPackage("com.google.android.apps.maps")
            holder.itemView.context.startActivity(mapIntent)
        }
    }

    override fun getItemCount(): Int = reservationList.size

}

class ReservationHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    val time :TextView = itemView.findViewById(R.id.txtTime_reserve)
    val storeNameReservation : TextView = itemView.findViewById(R.id.txtStoreName_reserve)
    val branchNameReservation : TextView = itemView.findViewById(R.id.txtStoreBranch_reserve)
    val location : ImageView = itemView.findViewById(R.id.IVLocation_reserve)
    lateinit var loc:String

}

