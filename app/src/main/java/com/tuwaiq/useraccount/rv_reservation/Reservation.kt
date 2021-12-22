package com.tuwaiq.useraccount.rv_reservation

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.*
import com.tuwaiq.useraccount.R


class Reservation : Fragment() {
    private lateinit var db:FirebaseFirestore
    val uId = FirebaseAuth.getInstance().currentUser?.uid
    private lateinit var reservationRV: RecyclerView
    private lateinit var reservationAdapter: ReservationAdapter
    private lateinit var rList:MutableSet<ReservationData>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_reservation, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        reservationRV = view.findViewById(R.id.reservationRV)
        reservationRV.layoutManager = LinearLayoutManager(this.context)
        reservationRV.setHasFixedSize(true)
        rList = mutableSetOf()
        reservationAdapter = ReservationAdapter(rList)
        reservationRV.adapter = reservationAdapter
        getTheReservationList()
    }


    private fun getTheReservationList() {

        db = FirebaseFirestore.getInstance()
        val id =FirebaseAuth.getInstance().currentUser?.uid
        db.collection("StoreOwner").document("5MwRVuvViFadf17yElZuB2puYXt1")
            .collection("Reservation")
            .addSnapshotListener(object : EventListener<QuerySnapshot> {

                @SuppressLint("NotifyDataSetChanged")
                override fun onEvent(value: QuerySnapshot?, error: FirebaseFirestoreException?) {
                    if(error != null){

                        Log.e("Firestore Add", error.message.toString())
                        return
                    }
                    for (dc: DocumentChange in value?.documentChanges!!){
                        if (dc.type == DocumentChange.Type.ADDED){

                           rList.add(dc.document.toObject(ReservationData::class.java))
                        }
                    }
                    reservationAdapter.notifyDataSetChanged()
                }
            })
    }

}