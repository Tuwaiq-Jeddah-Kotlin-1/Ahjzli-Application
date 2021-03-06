package com.tuwaiq.useraccount.rv_reservation

import android.annotation.SuppressLint
import android.graphics.Canvas
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.*
import com.tuwaiq.useraccount.R
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator


class Reservation : Fragment() {
    private lateinit var db:FirebaseFirestore
    private lateinit var reservationRV: RecyclerView
    private lateinit var reservationAdapter: ReservationAdapter
    private lateinit var rList:MutableList<ReservationData>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_reservation, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        reservationRV = view.findViewById(R.id.reservationRV)
        reservationRV.layoutManager = LinearLayoutManager(this.context)
        reservationRV.setHasFixedSize(true)
        rList = mutableListOf()
        reservationAdapter = ReservationAdapter(rList)
        reservationRV.adapter = reservationAdapter

        getTheReservationList()

        val taskTouchHelper= ItemTouchHelper(simpleCallback)
        taskTouchHelper.attachToRecyclerView(reservationRV)
    }

    private var simpleCallback = object : ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT.or(ItemTouchHelper.RIGHT)){
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            return true
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val position = viewHolder.layoutPosition
            val delete = rList[position]
            when(direction){
                ItemTouchHelper.LEFT -> {
                    db.collection("Reservation").document(delete.idRq).delete()
                    rList.remove(delete)
                    ReservationAdapter(rList)
                        .notifyItemRemoved(position)
                    addNumberTheOwner(delete.ownerId,delete.numberOfTheCustomer)
                }
                ItemTouchHelper.RIGHT -> {
                    db.collection("Reservation").document(delete.idRq).delete()
                    rList.remove(delete)

                    ReservationAdapter(rList).notifyItemRemoved(position)
                    addNumberTheOwner(delete.ownerId,delete.numberOfTheCustomer)
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

    private fun addNumberTheOwner(ownerId:String, numberOfTheCustomer:Int) {
        db.collection("StoreOwner").document(ownerId)
            .get().addOnCompleteListener {
                if (it.result?.exists()!!) {
                    val maxP = it.result!!.get("maxPeople").toString().toInt()
                    db.collection("StoreOwner").document(ownerId)
                        .update("maxPeople", numberOfTheCustomer + maxP)
                }
            }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun getTheReservationList() {
        db = FirebaseFirestore.getInstance()
        val id =FirebaseAuth.getInstance().currentUser?.uid
        db.collection("Reservation").whereEqualTo("userId", id.toString())
            .addSnapshotListener { value, _ ->
                for (dc: DocumentChange in value?.documentChanges!!) {
                    if (dc.type == DocumentChange.Type.ADDED) {

                        rList.add(dc.document.toObject(ReservationData::class.java))
                    } else {
                        rList.remove(dc.document.toObject(ReservationData::class.java))
                    }
                }
                reservationAdapter.notifyDataSetChanged()
            }
    }


}


