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
import androidx.core.widget.addTextChangedListener
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

    private val uId = FirebaseAuth.getInstance().currentUser?.uid
    private var db = FirebaseFirestore.getInstance()
    private lateinit var sName: TextView
    private lateinit var bName: TextView
    private lateinit var numberOfPeople: EditText
    private lateinit var maxCapacity:TextView
    private lateinit var reservationButton: Button
    private lateinit var name: String
    private lateinit var phone: String
    private var enterNumber:Int = 1
    private var maxP:Int=0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_item_list_dialog_list_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        sName = view.findViewById(R.id.txt_storeName_reservation)
        bName = view.findViewById(R.id.txt_branchName_reservation)
        numberOfPeople = view.findViewById(R.id.txtNumberOfPeople)
        reservationButton = view.findViewById(R.id.btnReservation)
        maxCapacity = view.findViewById(R.id.max_capacity)

        maxP = args.storeData.maxPeople
        if ( maxP == 0){
            numberOfPeople.isEnabled = false
            numberOfPeople.setText("0")
        }
        maxCapacity.text = maxP.toString()

        //reserved the data from the rv
        sName.text = args.storeData.storeName
        bName.text = args.storeData.branchName

        //get the name and the phone number of the user
        db.collection("UserAccount").document("$uId")
            .get().addOnCompleteListener {
                if (it.result?.exists()!!) {
                    name = it.result!!.getString("userName").toString()
                    phone = it.result!!.getString("number").toString()
                }
            }

        numberOfPeople.addTextChangedListener { capacity ->
            if (capacity.toString().isNotBlank() && capacity.toString().toIntOrNull() == null){
                numberOfPeople.setText(capacity?.substring(0,capacity.length -1))
            }
            if (capacity.toString().toIntOrNull() != null) {
                if (capacity.toString().toInt() > maxP) {
                    numberOfPeople.setText(maxP.toString())
                    Toast.makeText(
                        context, "You can't reserve more than: $maxP",
                        Toast.LENGTH_SHORT
                    ).show()
                }else if (capacity.toString().toInt() < 1){
                    numberOfPeople.setText("1")
                }
            }

        }

        reservationButton.setOnClickListener {

            if (numberOfPeople.text.isNotEmpty()) {
                enterNumber = numberOfPeople.text.toString().toInt()
                if (maxP == 0){
                    Toast.makeText(
                        context, "there is no more space",
                        Toast.LENGTH_SHORT
                    ).show()
                }else {
                    if (enterNumber <= maxP) {
                        maxP -= enterNumber
                        addReserve()
                        upDateTheNumberOfPeople()
                    }
                }
            }else{
                Toast.makeText(
                    context, "enter a number ",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun upDateTheNumberOfPeople(){
        val upDateUserData = Firebase.firestore.collection("StoreOwner")
        upDateUserData.document(args.storeData.idOwner).update("maxPeople", maxP)
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
        reserve.branchName = args.storeData.branchName
        reserve.storeName = args.storeData.storeName
        reserve.maps = args.storeData.branchLocation
        reserve.numberOfTheCustomer = numberOfPeople.text.toString().toInt()
        reserve.userId = uId.toString()
        reserve.userName = name
        reserve.userPhone = phone
        reserve.date = formatted
        db.collection("Reservation").document(reserve.idRq).set(reserve)
        findNavController().navigate(R.id.reservation)


    }

}