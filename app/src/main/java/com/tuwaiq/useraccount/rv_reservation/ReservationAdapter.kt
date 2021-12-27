package com.tuwaiq.useraccount.rv_reservation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
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

        holder.storeNameReservation.text = reserve.storeName
        holder.branchNameReservation.text = reserve.branchName
        holder.loc = reserve.maps
        holder.location.setOnClickListener {
            Toast.makeText(holder.itemView.context, reserve.maps,Toast.LENGTH_SHORT).show()
        }
    }

    override fun getItemCount(): Int = reservationList.size

}

class ReservationHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    var storeNameReservation : TextView = itemView.findViewById(R.id.txtRestaurantName_reserve)
    var branchNameReservation : TextView = itemView.findViewById(R.id.txtRestaurantBranch_reserve)
    var location : ImageView = itemView.findViewById(R.id.IVLocation_reserve)
    lateinit var loc:String
}

