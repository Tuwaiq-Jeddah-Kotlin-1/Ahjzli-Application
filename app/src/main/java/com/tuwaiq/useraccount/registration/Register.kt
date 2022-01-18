package com.tuwaiq.useraccount.registration

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.tuwaiq.useraccount.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class Register : Fragment() {
    private lateinit var haveAccount: TextView
    private lateinit var userNameAccount: TextInputEditText
    private lateinit var emailAccount: TextInputEditText
    private lateinit var passwordAccount: TextInputEditText
    private lateinit var phoneNumberAccount: TextInputEditText
    private lateinit var signUpButton: Button
    private lateinit var progressBar: ProgressBar
    private lateinit var sharedPreferences: SharedPreferences
    private val db = Firebase.firestore.collection("UserAccount")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        sharedPreferences = this.requireActivity().getSharedPreferences("Profile", Context.MODE_PRIVATE)

        //back to sign in
        haveAccount = view.findViewById(R.id.txt_have_account)
        haveAccount.setOnClickListener {
            findNavController().navigate(RegisterDirections.actionRegisterToSignIn())
        }

        userNameAccount = view.findViewById(R.id.tiet_userName_sign_up)
        emailAccount = view.findViewById(R.id.tiet_email_sign_up)
        phoneNumberAccount = view.findViewById(R.id.tiet_Phone_sign_up)
        passwordAccount = view.findViewById(R.id.tiet_password_sign_up)
        progressBar = view.findViewById(R.id.progressBarRegister)

        //sign up
        signUpButton = view.findViewById(R.id.btnSignUp)
        signUpButton.setOnClickListener {
            signUpButton.isClickable = false
            progressBar.isVisible = true
            registerUser()
        }
    }

    // registerUser()
    private fun registerUser() {
        val userName = userNameAccount.text.toString()
        val email: String = emailAccount.text.toString().trim()
        val password = passwordAccount.text.toString().trim()
        val phoneNumber = phoneNumberAccount.text.toString()
        val account = MyAccountData(userName, email, phoneNumber)
        if (userName.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty() && phoneNumber.isNotEmpty()) {
            //phone number must be 10
            if (phoneNumber.length == 10) {
                //save to the Authentication
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            saveAccount(account)
                        } else {
                            // if the registration is not successful then show error massage
                            Toast.makeText(
                                context,
                                "Please make sure the values are correct, or fill the fields",
                                Toast.LENGTH_LONG
                            ).show()
                            signUpButton.isClickable = true
                            progressBar.isVisible = false
                        }
                    }
            }else{
                Toast.makeText(context, "please enter a saudi phone number, 05XXXXXXXX", Toast.LENGTH_LONG)
                    .show()
                signUpButton.isClickable = true
                progressBar.isVisible = false
            }
        } else {
            Toast.makeText(context, "please enter all fields", Toast.LENGTH_LONG)
                .show()
            signUpButton.isClickable = true
            progressBar.isVisible = false
        }
    }

    //push to fire store
    private fun saveAccount(account: MyAccountData) = CoroutineScope(Dispatchers.IO).launch {
        val uid = FirebaseAuth.getInstance().currentUser?.uid
            db.document("$uid").set(account)
            withContext(Dispatchers.Main) {
                getUserInfo()
                findNavController().navigate(RegisterDirections.actionRegisterToMainView())
            }
    }
    private fun getUserInfo() = CoroutineScope(Dispatchers.IO).launch {
        val uId =FirebaseAuth.getInstance().currentUser?.uid
            val db = FirebaseFirestore.getInstance()
            db.collection("UserAccount").document("$uId")
                .get().addOnCompleteListener {
                    if (it.result?.exists()!!) {
                        val name = it.result!!.getString("userName")
                        val userEmail = it.result!!.getString("emailAddress")
                        val userPhone = it.result!!.getString("number")

                        //to save the info in the sp
                        sharedPreferences.edit()
                        .putString("spUserName",name.toString())
                        .putString("spEmail",userEmail.toString())
                        .putString("spPhoneNumber",userPhone.toString())
                        .apply()
                    } else {
                        Log.e("error", "failed to upload user info")
                        signUpButton.isClickable = true
                        progressBar.isVisible = false
                    }
                }
    }
}