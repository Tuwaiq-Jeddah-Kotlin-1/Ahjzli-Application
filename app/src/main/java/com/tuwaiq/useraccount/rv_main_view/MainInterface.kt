package com.tuwaiq.useraccount.rv_main_view

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.*
import com.tuwaiq.useraccount.ui.MainActivity
import com.tuwaiq.useraccount.R
import com.tuwaiq.useraccount.notification.AhjzliNotificationRepo


class MainInterface : Fragment() {

    private lateinit var rv:RecyclerView
    private lateinit var myAdapter:MainViewAdapter
    private val db =FirebaseFirestore.getInstance()
    private lateinit var search:SearchView
    private lateinit var rList:MutableList<GetStoreData>
    private lateinit var shared: SharedPreferences


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main_view, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        shared = this.requireActivity().getSharedPreferences(
            "storeCount", Context.MODE_PRIVATE)

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
    @SuppressLint("NotifyDataSetChanged")
    private fun getTheDataList() {
            db.collection("StoreOwner").whereEqualTo("publish",true)
            .addSnapshotListener { value, _ ->
                if (value != null) {
                    myAdapter = MainViewAdapter(value.toObjects(GetStoreData::class.java))
                    rv.adapter = myAdapter
                }
                for (dc: DocumentChange in value?.documentChanges!!) {
                    if (dc.type == DocumentChange.Type.ADDED) {
                        rList.add(dc.document.toObject(GetStoreData::class.java))
                        rv.adapter = myAdapter

                        //check if there new list send notification
                        val count = rList.size
                        val sp = shared.getInt("count", 0)
                        if (sp < count) {
                            countOfTheList(count)
                            val name = dc.document.data["storeName"]
                            AhjzliNotificationRepo().myNotification(
                                MainActivity(),
                                "$name added, reserved now!!"
                            )
                        }

                    } else {
                        rList.remove(dc.document.toObject(GetStoreData::class.java))
                        rv.adapter = myAdapter

                        //if there is an update in the list
                        val updateCount = rList.size
                        shared.edit()
                        .putInt("count", updateCount)
                            .apply()
                    }
                }
                myAdapter.notifyDataSetChanged()
            }
    }
    private fun countOfTheList(count:Int){
        shared.edit()
        .putInt("count",count)
            .apply()
    }
}