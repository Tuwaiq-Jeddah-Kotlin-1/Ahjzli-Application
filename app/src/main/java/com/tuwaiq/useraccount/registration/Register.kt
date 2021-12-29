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
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.NavDirections
import androidx.navigation.findNavController
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
import java.lang.Exception


class Register : Fragment() {
    private lateinit var haveAccount: TextView
    private lateinit var userNameAccount: TextInputEditText
    private lateinit var emailAccount: TextInputEditText
    private lateinit var passwordAccount: TextInputEditText
    private lateinit var phoneNumberAccount: TextInputEditText
    private lateinit var signUpButton: Button
    private lateinit var sharedPreferences2: SharedPreferences
    private val db = Firebase.firestore.collection("UserAccount")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_register, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        sharedPreferences2 = this.requireActivity().getSharedPreferences("Profile", Context.MODE_PRIVATE)

        //back to sign in
        haveAccount = view.findViewById(R.id.txt_have_account)
        haveAccount.setOnClickListener {
            val action: NavDirections = RegisterDirections.actionRegisterToSignIn()
            view.findNavController().navigate(action)
        }

        userNameAccount = view.findViewById(R.id.tiet_userName_sign_up)
        emailAccount = view.findViewById(R.id.tiet_email_sign_up)
        phoneNumberAccount = view.findViewById(R.id.tiet_Phone_sign_up)
        passwordAccount = view.findViewById(R.id.tiet_password_sign_up)

        //sign up
        signUpButton = view.findViewById(R.id.btnSignUp)
        signUpButton.setOnClickListener {
            registerUser()
        }


    }

    // registerUser()
    private fun registerUser() {
        val userName = userNameAccount.text.toString()
        val email: String = emailAccount.text.toString().trim { it <= ' ' }
        val password = passwordAccount.text.toString().trim { it <= ' ' }
        //Phone number must be 10
        val phoneNumber = phoneNumberAccount.text.toString()
        val account = MyAccountData(userName, email, phoneNumber)

        if (userName.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty() && phoneNumber.isNotEmpty()) {
            //save to the Authentication
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(context, "You were registered successful", Toast.LENGTH_LONG)
                            .show()

                        saveAccount(account)

                    } else {
                        // if the registration is not successful then show error massage
                        Toast.makeText(
                            context, "Please make sure the values are correct, or fill the fields",
                            Toast.LENGTH_LONG
                        ).show()

                    }
                }
        } else {
            Toast.makeText(context, "please enter all fields", Toast.LENGTH_LONG)
                .show()
        }
    }


    //push to fire store
    private fun saveAccount(account: MyAccountData) = CoroutineScope(Dispatchers.IO).launch {
        val uid = FirebaseAuth.getInstance().currentUser?.uid
        try {
            db.document("$uid").set(account)
            withContext(Dispatchers.Main) {
                getUserInfo()
                val action: NavDirections = RegisterDirections.actionRegisterToMainView()
                view?.findNavController()?.navigate(action)
                //saveSharedPreference()
                Toast.makeText(context, "saved data", Toast.LENGTH_LONG).show()
            }

        } catch (e: Exception) {
            withContext(Dispatchers.Main) {
                Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
            }
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

                        //to save the info in the sp

                        val editor3:SharedPreferences.Editor = sharedPreferences2.edit()
                        editor3.putString("spUserName",name.toString())
                        editor3.putString("spEmail",userEmail.toString())
                        editor3.putString("spPhoneNumber",userPhone.toString())
                        editor3.apply()

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
}