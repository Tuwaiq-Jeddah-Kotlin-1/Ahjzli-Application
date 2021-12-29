package com.tuwaiq.useraccount.rv_reservation

import android.annotation.SuppressLint
import android.graphics.Canvas
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.*
import com.tuwaiq.useraccount.R
import com.tuwaiq.useraccount.rv_main_view.GetStoreData
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator


class Reservation : Fragment() {
    private lateinit var db:FirebaseFirestore
    private lateinit var reservationRV: RecyclerView
    private lateinit var reservationAdapter: ReservationAdapter
    private lateinit var rList:MutableList<ReservationData>
    private var enterNumber:Int = 1
    private  var maxP:Int=0


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
        rList = mutableListOf()
        reservationAdapter = ReservationAdapter(rList)
        reservationRV.adapter = reservationAdapter

        val taskTouchHelper= ItemTouchHelper(simpleCallback)
        taskTouchHelper.attachToRecyclerView(reservationRV)

        getTheReservationList()
    }

    private var simpleCallback= object : ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT.or(ItemTouchHelper.RIGHT)){
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            return true
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val position = viewHolder.layoutPosition
            val deletedFav = rList[position]
            when(direction){
                ItemTouchHelper.LEFT -> {
                    deleteReservation(deletedFav)
                    rList.remove(deletedFav)

                    ReservationAdapter(rList).notifyItemRemoved(position)
                }
                ItemTouchHelper.RIGHT -> {
                    deleteReservation(deletedFav)
                    rList.remove(deletedFav)
                    ReservationAdapter(rList).notifyItemRemoved(position)
                }
            }
        }
        override fun onChildDraw(c: Canvas, recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean) {
            RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                .addSwipeLeftBackgroundColor(android.graphics.Color.parseColor("#E80000"))
                .addSwipeRightBackgroundColor(android.graphics.Color.parseColor("#E80000"))
                .addSwipeLeftActionIcon(R.drawable.ic_delete)
                .addSwipeRightActionIcon(R.drawable.ic_delete)
                .create()
                .decorate()
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
        }
    }
    private fun deleteReservation(delete:ReservationData){
        db.collection("Reservation").document(delete.idRq).delete()
    }

    private fun getTheReservationList() {
        db = FirebaseFirestore.getInstance()
        val id =FirebaseAuth.getInstance().currentUser?.uid
        db.collection("Reservation").whereEqualTo("userId", id.toString())
            .addSnapshotListener(object : EventListener<QuerySnapshot> {

                @SuppressLint("NotifyDataSetChanged")
                override fun onEvent(value: QuerySnapshot?, error: FirebaseFirestoreException?) {
                    if (error != null) {
                        Log.e("Firestore Add", error.message.toString())
                        return
                    }
                    for (dc: DocumentChange in value?.documentChanges!!) {
                        if (dc.type == DocumentChange.Type.ADDED) {

                            rList.add(dc.document.toObject(ReservationData::class.java))
                        }
                    }
                    reservationAdapter.notifyDataSetChanged()
                }
            })
        }


}


