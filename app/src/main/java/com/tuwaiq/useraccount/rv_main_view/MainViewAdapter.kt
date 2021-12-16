package com.tuwaiq.useraccount.rv_main_view

import android.text.Layout
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.tuwaiq.useraccount.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewAdapter(private val storeList:List<GetStoreData>): RecyclerView.Adapter<CustomHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomHolder {
        // inflate layout
        val view = LayoutInflater.from(parent.context).inflate(R.layout.restaurant_rv_list,parent,false)
        return CustomHolder(view)
    }

    override fun onBindViewHolder(holder: CustomHolder, position: Int) {
        val store = storeList[position]
        holder.sName.text = store.storeName
        holder.storeBName.text = store.branchName
        holder.map = store.branchLocation
        holder.storeBLocation.setOnClickListener{
            Toast.makeText(holder.itemView.context," map is ${store.branchLocation}",Toast.LENGTH_LONG).show()
        }
    }

    //take the size
    override fun getItemCount(): Int = storeList.size

    }


//init the values
class CustomHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    val sName:TextView = itemView.findViewById(R.id.txtRestaurantName)
    val storeBName:TextView = itemView.findViewById(R.id.txtRestaurantBranch)
    val storeBLocation:ImageView = itemView.findViewById(R.id.ivLocation)
   lateinit var map:String
}
