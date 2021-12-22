package com.tuwaiq.useraccount.rv_main_view


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.findFragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.tuwaiq.useraccount.R

class MainViewAdapter(private val storeList:Set<GetStoreData>,
                      ): RecyclerView.Adapter<CustomHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomHolder {
        // inflate layout
        val view = LayoutInflater.from(parent.context).inflate(R.layout.restaurant_rv_list,parent,false)
        return CustomHolder(view)
    }

    override fun onBindViewHolder(holder: CustomHolder, position: Int) {
        val store = storeList.toList()[position]
        holder.sName.text = store.storeName
        holder.storeBName.text = store.branchName
        holder.map = store.branchLocation
        holder.idOwner =store.idOwner
        holder.storeBLocation.setOnClickListener{
            Toast.makeText(holder.itemView.context," map is ${store.branchLocation}",Toast.LENGTH_LONG).show()
        }
    }
    //take the size
    override fun getItemCount(): Int = storeList.size

    }


//init the values
class CustomHolder(itemView: View): RecyclerView.ViewHolder(itemView),View.OnClickListener {

    val sName: TextView = itemView.findViewById(R.id.txtRestaurantName_reserve)
    val storeBName: TextView = itemView.findViewById(R.id.txtRestaurantBranch)
    val storeBLocation: ImageView = itemView.findViewById(R.id.etLocation_reserve)
    lateinit var map: String
    lateinit var idOwner:String


    init {
        itemView.setOnClickListener (this)
    }

    override fun onClick(v: View?) {
        val parcelize =GetStoreData()
        parcelize.branchName = storeBName.text.toString()
        parcelize.storeName = sName.text.toString()
        parcelize.idOwner = idOwner.toString()
        parcelize.branchLocation =map
        parcelize.branchLocation = storeBLocation.toString()
        val action: NavDirections =
                MainInterfaceDirections.actionMainViewToItemListDialogFragment(parcelize)
           findNavController(itemView.findFragment()).navigate(action)
        }

    }



