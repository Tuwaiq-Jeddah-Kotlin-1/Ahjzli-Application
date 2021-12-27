package com.tuwaiq.useraccount.bottomsheet_reservation

import android.os.Bundle
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.tuwaiq.useraccount.R
import com.tuwaiq.useraccount.rv_reservation.ReservationData
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class ItemListDialogFragment : BottomSheetDialogFragment() {
    private val args by navArgs<ItemListDialogFragmentArgs>()

    val uId = FirebaseAuth.getInstance().currentUser?.uid
    private lateinit var sName: TextView
    private lateinit var bName: TextView
    private lateinit var numberOfPeople: EditText
    private lateinit var reservationButton: Button
    private var db = FirebaseFirestore.getInstance()
    private lateinit var name: String
    private lateinit var phone: String
    private var enterNumber:Int = 1
    private  var maxP:Int=0


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =
            inflater.inflate(R.layout.fragment_item_list_dialog_list_dialog, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        sName = view.findViewById(R.id.txt_storeName_reservation)
        bName = view.findViewById(R.id.txt_branchName_reservation)
        numberOfPeople = view.findViewById(R.id.txtNumberOfPeople)
        reservationButton = view.findViewById(R.id.btnReservation)

        //reserved the data from the rv
        sName.setText(args.storeData.storeName)
        bName.setText(args.storeData.branchName)


        //get the name and the phone number of the user
        val db2 = FirebaseFirestore.getInstance()
        db2.collection("UserAccount").document("$uId")
            .get().addOnCompleteListener {
                if (it.result?.exists()!!) {
                    name = it.result!!.getString("userName").toString()
                    phone = it.result!!.getString("number").toString()
                }
            }


        reservationButton.setOnClickListener {

            maxP=args.storeData.maxPeople
            enterNumber = numberOfPeople.text.toString().toInt()
            //Toast.makeText(context,"max $maxP",Toast.LENGTH_SHORT).show()

            if ( enterNumber <= maxP){
                maxP -= enterNumber
                Toast.makeText(context,"max $maxP",Toast.LENGTH_SHORT).show()
                addReserve()
                upDateTheNumberOfPeople()
            }else{
                Toast.makeText(context,"You can reserve: $maxP more",Toast.LENGTH_SHORT).show()
            }
        }
    }


    private fun upDateTheNumberOfPeople(){
        val upDateUserData = Firebase.firestore.collection("StoreOwner")
        upDateUserData.document(args.storeData.idOwner).update("maxPeople", maxP)
        Toast.makeText(context,"edit is successful",Toast.LENGTH_LONG).show()
    }



    private fun addReserve() {
        //reserve Date and time
        val current = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-SS")
        val formatted = current.format(formatter)

        val requestId= FirebaseAuth.getInstance().currentUser?.uid

        val reserve = ReservationData()
        reserve.ownerId = args.storeData.idOwner
        reserve.idRq = "$requestId $formatted"
        reserve.branchName = args.storeData.storeName
        reserve.storeName = args.storeData.branchName
        reserve.maps = args.storeData.branchLocation
        reserve.numberOfTheCustomer = numberOfPeople.text.toString().toInt()
        reserve.userId = uId.toString()
        reserve.userName = name
        reserve.userPhone = phone
        reserve.date = formatted
        db.collection("Reservation").document(reserve.idRq).set(reserve)
        findNavController().navigate(ItemListDialogFragmentDirections.actionItemListDialogFragmentToReservation())

    }
}