package com.tuwaiq.useraccount.rv_main_view


import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.findFragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.tuwaiq.useraccount.R
import java.util.*
import kotlin.collections.ArrayList

private const val TAG = "MainViewAdapter"
class MainViewAdapter(var  storeFilterList: MutableList<GetStoreData>,
                    ): RecyclerView.Adapter<CustomHolder>(),Filterable {
    var storeList = mutableListOf<GetStoreData>()

    init {
        Log.d(TAG, "$storeFilterList: ")
        storeFilterList.forEach {
            storeList.add(it)
            Log.d(TAG, "$it: ")
        }
    }

    override fun getFilter(): Filter = object : Filter(){
        override fun performFiltering(constraint: CharSequence?): FilterResults? {
            val filteredList: MutableList<GetStoreData> = ArrayList()

            if (constraint == null || constraint.isEmpty()) {
                filteredList.addAll(storeList)
                Log.d(TAG, "performFiltering: $storeList")
            } else {
                val filterPattern = constraint.toString().lowercase(Locale.getDefault()).trim { it <= ' ' }
                for (item in storeList) {
                    if (item.storeName.lowercase(Locale.getDefault()).contains(filterPattern)) {
                        filteredList.add(item)
                    }
                }
            }
            val results = FilterResults()
            results.values = filteredList
            return results
        }

        override fun publishResults(constraint: CharSequence?, results: FilterResults) {
            storeFilterList.clear()
            storeFilterList.addAll(results.values as List<GetStoreData>)
            notifyDataSetChanged()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomHolder {
        // inflate layout
        val view = LayoutInflater.from(parent.context).inflate(R.layout.main_rv_list,parent,false)
        return CustomHolder(view)
    }

    override fun onBindViewHolder(holder: CustomHolder, position: Int) {
        val store = storeFilterList.toList()[position]
        holder.sName.text = store.storeName
        holder.storeBName.text = store.branchName
        holder.map = store.branchLocation
        holder.idOwner =store.idOwner
       holder.maxP = store.maxPeople

    }
    //take the size
    override fun getItemCount(): Int = storeFilterList.size
}

//init the values
class CustomHolder(itemView: View): RecyclerView.ViewHolder(itemView),View.OnClickListener {

    val sName: TextView = itemView.findViewById(R.id.txtStoreName_main)
    val storeBName: TextView = itemView.findViewById(R.id.txtStoreBranch_main)
    val storeBLocation: ImageView = itemView.findViewById(R.id.etLocation_main)
    lateinit var map: String
    lateinit var idOwner:String
      var maxP:Int =1


    init {
        itemView.setOnClickListener (this)
    }

    override fun onClick(v: View?) {
        val parcelize =GetStoreData()
        parcelize.branchName = storeBName.text.toString()
        parcelize.storeName = sName.text.toString()
        parcelize.idOwner = idOwner
        parcelize.branchLocation =map
       parcelize.maxPeople = maxP

        storeBLocation.setOnClickListener{
            val gmmIntentUri = Uri.parse("geo:$map?q=$map")
            val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
            mapIntent.setPackage("com.google.android.apps.maps")
            this.itemView.context.startActivity(mapIntent)
        }

        val action: NavDirections =
                MainInterfaceDirections.actionMainViewToItemListDialogFragment(parcelize)
           findNavController(itemView.findFragment()).navigate(action)
        }

}



