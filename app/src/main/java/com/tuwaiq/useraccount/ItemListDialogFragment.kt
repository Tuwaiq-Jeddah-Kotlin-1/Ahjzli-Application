package com.tuwaiq.useraccount

import android.os.Bundle
import android.util.Log
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.fragment.navArgs
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ItemListDialogFragment : BottomSheetDialogFragment() {
    private val args by navArgs<ItemListDialogFragmentArgs>()

    val uId = FirebaseAuth.getInstance().currentUser?.uid
    private lateinit var sName:TextView
    private lateinit var bName:TextView
    private lateinit var reservationButton: Button


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
            val view = inflater.inflate(R.layout.fragment_item_list_dialog_list_dialog, container, false)
        //getStoreInfo()
            return view

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        sName = view.findViewById(R.id.txt_storeName_reservation)
        bName = view.findViewById(R.id.txt_branchName_reservation)
        reservationButton = view.findViewById(R.id.btnReservation)

        Toast.makeText(context,"lolo ${args.storeData.storeName}",Toast.LENGTH_LONG).show()
        sName.setText(args.storeData.storeName)
        bName.setText(args.storeData.branchName)

    }

    /*fun getStoreInfo() = CoroutineScope(Dispatchers.IO).launch {
        try {
            val db = FirebaseFirestore.getInstance()
            db.collection("StoreOwner").document("$uId")
                .get().addOnCompleteListener {
                    if (it.result?.exists()!!) {
                        val storeName = it.result!!.getString("storeName")
                        val branchName = it.result!!.getString("branchName")
                        sName.text= storeName.toString()
                        bName.text= branchName.toString()

                    } else {
                        Log.e("error \n", "errooooooorr")
                    }
                }

        } catch (e: Exception) {
            withContext(Dispatchers.Main) {
                // Toast.makeText(coroutineContext,0,0, e.message, Toast.LENGTH_LONG).show()
                Log.e("FUNCTION createUserFirestore", "${e.message}")
            }
        }
    }*/
}