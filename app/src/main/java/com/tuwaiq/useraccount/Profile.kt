package com.tuwaiq.useraccount

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class Profile : Fragment() {
    private lateinit var userNameProfile:TextView
    private lateinit var emailProfile:TextView
    private lateinit var phoneNumberProfile:TextView
    private lateinit var editButton:Button
    private lateinit var logOut:TextView
    private lateinit var sharedPreferences: SharedPreferences

    //bottom sheet
    private lateinit var userNameBS:EditText
    private lateinit var phoneNumberBS:EditText
    private lateinit var editBS:Button


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)
        getUserInfo()
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        //shared preference
        sharedPreferences = this.requireActivity().getSharedPreferences("preference", Context.MODE_PRIVATE)

        userNameProfile= view.findViewById(R.id.txt_userName_profile)
        emailProfile= view.findViewById(R.id.txt_email_Profile)
        phoneNumberProfile= view.findViewById(R.id.txt_Phone_profile)
        editButton = view.findViewById(R.id.btnEdit)
        logOut = view.findViewById(R.id.logOut)

        editButton.setOnClickListener {
            bottomSheet()
        }
        logOut.setOnClickListener {
            getSPForLogOut()
        }
    }

    fun getUserInfo() = CoroutineScope(Dispatchers.IO).launch {

        val uId =FirebaseAuth.getInstance().currentUser?.uid
        try {
            val db = FirebaseFirestore.getInstance()
            db.collection("UserAccount").document("$uId")
                .get().addOnCompleteListener {
                    if (it.result?.exists()!!) {
                        val name = it.result!!.getString("userName")
                        val userEmail = it.result!!.getString("emailAddress")
                        val userPhone = it.result!!.getString("number")

                       userNameProfile.text= name.toString()
                       emailProfile.text= userEmail.toString()
                       phoneNumberProfile.text= userPhone.toString()

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
    }
    private fun getSPForLogOut(){
        val editor:SharedPreferences.Editor = sharedPreferences.edit()
        sharedPreferences.getString("EMAIL","")
        sharedPreferences.getString("PASSWORD","")
        editor.clear()
        editor.apply()
        findNavController().navigate(ProfileDirections.actionProfileToSignIn())
    }

    fun bottomSheet() {
        val view: View = layoutInflater.inflate(R.layout.bottom_sheet, null)
        userNameBS = view.findViewById(R.id.et_userName_profile)
        phoneNumberBS = view.findViewById(R.id.et_PhoneNumber)
        editBS = view.findViewById(R.id.btnEditConfirm)

        userNameBS.setText(userNameProfile.text.toString())
        phoneNumberBS.setText(phoneNumberProfile.text.toString())

        editBS.setOnClickListener {
            editProfile()
        }
        val builder = BottomSheetDialog(requireView()?.context)
        builder.setTitle("edit")
        builder.setContentView(view)
        builder.show()
    }

    private fun editProfile(){
        val uId = FirebaseAuth.getInstance().currentUser?.uid
        val upDateUserData = Firebase.firestore.collection("UserAccount")
        upDateUserData.document(uId.toString()).update("userName", userNameBS.text.toString())
        upDateUserData.document(uId.toString()).update("number", phoneNumberBS.text.toString())
        Toast.makeText(context,"edit is successful",Toast.LENGTH_LONG).show()
    }
}