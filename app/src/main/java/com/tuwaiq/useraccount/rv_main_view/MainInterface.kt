package com.tuwaiq.useraccount.rv_main_view

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.SearchView
import androidx.core.view.contains
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.firestore.*
import com.google.firebase.firestore.EventListener
import com.google.protobuf.Value
import com.tuwaiq.useraccount.MainActivity
import com.tuwaiq.useraccount.R
import com.tuwaiq.useraccount.notification.AhjzliNotificationRepo
import com.tuwaiq.useraccount.rv_reservation.ReservationData
import org.w3c.dom.Document
import java.util.*
import kotlin.collections.ArrayList
import android.widget.ListAdapter as ListAdapter


class MainInterface : Fragment() {

    private lateinit var rv:RecyclerView
    private lateinit var myAdapter:MainViewAdapter
    private lateinit var db:FirebaseFirestore
    private lateinit var search:SearchView
    private lateinit var rList:MutableList<GetStoreData>
    private lateinit var storeName:DocumentChange


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_main_view, container, false)
        return view
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        search = view.findViewById(R.id.searchView)
        rv = view.findViewById(R.id.storeRV)
        rv.layoutManager = LinearLayoutManager(this.context)
        rList = mutableListOf()
        myAdapter = MainViewAdapter(rList)
        rv.setHasFixedSize(true)
        getTheDataList()

        //search view
        search.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                myAdapter.filter.filter(newText)
                return false
            }

        })
    }

    //check the state if true
    private fun getTheDataList() {
        db = FirebaseFirestore.getInstance()
            db.collection("StoreOwner").whereEqualTo("publish",true)
            .addSnapshotListener(object :EventListener<QuerySnapshot>{
                @SuppressLint("NotifyDataSetChanged")
                override fun onEvent(value: QuerySnapshot?, error: FirebaseFirestoreException?) {
                        if (value != null) {
                            value.documents.forEach {

                                Log.e("getTheDataList", "${it.data?.get("storeName")}")
                                AhjzliNotificationRepo().myNotification(MainActivity(),"${it.data!!.get("storeName")}")
                            }
                            myAdapter = MainViewAdapter(value.toObjects(GetStoreData::class.java))
                            rv.adapter = myAdapter
                        }
                }
            })
    }
}