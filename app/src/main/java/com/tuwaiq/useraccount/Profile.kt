package com.tuwaiq.useraccount

import android.annotation.SuppressLint
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
    private lateinit var userNameProfile: TextView
    private lateinit var emailProfile: TextView
    private lateinit var phoneNumberProfile: TextView
    private lateinit var editButton: Button
    private lateinit var logOut: TextView
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var sharedPreferences2: SharedPreferences

    //bottom sheet
    private lateinit var userNameBS: EditText
    private lateinit var phoneNumberBS: EditText
    private lateinit var editBS: Button


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)
        //getUserInfo()
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        //shared preference
        sharedPreferences =
            this.requireActivity().getSharedPreferences("preference", Context.MODE_PRIVATE)


        userNameProfile = view.findViewById(R.id.txt_userName_profile)
        emailProfile = view.findViewById(R.id.txt_email_Profile)
        phoneNumberProfile = view.findViewById(R.id.txt_Phone_profile)
        editButton = view.findViewById(R.id.btnEdit)
        logOut = view.findViewById(R.id.logOut)

        //get the info from the sp
        sharedPreferences2 =
            this.requireActivity().getSharedPreferences("Profile", Context.MODE_PRIVATE)
        val sp1 = sharedPreferences2.getString("spUserName", " ")
        val sp2 = sharedPreferences2.getString("spEmail", " ")
        val sp3 = sharedPreferences2.getString("spPhoneNumber", " ")
        userNameProfile.text = sp1
        emailProfile.text = sp2
        phoneNumberProfile.text = sp3


        editButton.setOnClickListener {
            bottomSheet()
        }
        logOut.setOnClickListener {
            getSPForLogOut()
        }
    }

    private fun getSPForLogOut() {
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        sharedPreferences.getString("EMAIL", "")
        sharedPreferences.getString("PASSWORD", "")
        editor.clear()
        editor.apply()

        val editor2: SharedPreferences.Editor = sharedPreferences2.edit()
        sharedPreferences2.getString("spUserName", " ")
        sharedPreferences2.getString("spEmail", " ")
        sharedPreferences2.getString("spPhoneNumber", " ")
        editor2.clear()
        editor2.apply()

        findNavController().navigate(ProfileDirections.actionProfileToSignIn())
    }


    private fun bottomSheet() {
        val view: View = layoutInflater.inflate(R.layout.bottom_sheet, null)
        userNameBS = view.findViewById(R.id.et_userName_profile)
        phoneNumberBS = view.findViewById(R.id.et_PhoneNumber)
        editBS = view.findViewById(R.id.btnEditConfirm)

        userNameBS.setText(userNameProfile.text.toString())
        phoneNumberBS.setText(phoneNumberProfile.text.toString())
        val builder = BottomSheetDialog(requireView().context)
        editBS.setOnClickListener {
            if (userNameBS.text.isNotEmpty() && phoneNumberBS.text.isNotEmpty()) {
                if (phoneNumberBS.text.length == 10) {
                    editProfile()
                    //save the changes in the sp
                    sharedPreferences2 =
                        requireActivity().getSharedPreferences("Profile", Context.MODE_PRIVATE)
                    val editor3: SharedPreferences.Editor = sharedPreferences2.edit()
                    editor3.putString("spUserName", userNameBS.text.toString())
                    editor3.putString("spPhoneNumber", phoneNumberBS.text.toString())
                    editor3.apply()
                    userNameProfile.text = userNameBS.text.toString()
                    phoneNumberProfile.text = phoneNumberBS.text.toString()
                    builder.dismiss()
                } else {
                    Toast.makeText(
                        context, "Phone number must be 10 numbers",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } else {
                Toast.makeText(
                    context, "fill the blanks",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        builder.setTitle("edit")
        builder.setContentView(view)
        builder.show()
    }

    private fun editProfile() {
        val uId = FirebaseAuth.getInstance().currentUser?.uid
        val upDateUserData = Firebase.firestore.collection("UserAccount")
        upDateUserData.document(uId.toString()).update("userName", userNameBS.text.toString())
        upDateUserData.document(uId.toString()).update("number", phoneNumberBS.text.toString())
        Toast.makeText(context, "edit is successful", Toast.LENGTH_LONG).show()

    }
}