package com.tuwaiq.useraccount.rv_main_view

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.*
import com.google.protobuf.Value
import com.tuwaiq.useraccount.R
import org.w3c.dom.Document


class MainInterface : Fragment() {

    private lateinit var rv:RecyclerView
    private lateinit var myAdapter:MainViewAdapter
    private lateinit var sList:MutableList<GetStoreData>
    private lateinit var db:FirebaseFirestore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_main_view, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        rv = view.findViewById(R.id.storeRV)
        rv.layoutManager = LinearLayoutManager(this.context)
        rv.setHasFixedSize(true)

        sList = mutableListOf()

        myAdapter = MainViewAdapter(sList)

        rv.adapter = myAdapter

        getTheDataList()

    }

    private fun getTheDataList() {
        db = FirebaseFirestore.getInstance()
            db.collection("StoreOwner")
            .addSnapshotListener(object :EventListener<QuerySnapshot>{
                @SuppressLint("NotifyDataSetChanged")
                override fun onEvent(value: QuerySnapshot?, error: FirebaseFirestoreException?) {
                    if(error != null){

                        Log.e("Firestore Error", error.message.toString())
                        return
                    }
                    for (dc:DocumentChange in value?.documentChanges!!){
                        if (dc.type == DocumentChange.Type.ADDED){
                            sList.add(dc.document.toObject(GetStoreData::class.java))
                        }
                    }
                    myAdapter.notifyDataSetChanged()
                }

            })
    }
}