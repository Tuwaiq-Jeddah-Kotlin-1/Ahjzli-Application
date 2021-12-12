package com.tuwaiq.useraccount.registration

import android.os.Bundle
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
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.tuwaiq.useraccount.MyAccountData
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
    private val db = Firebase.firestore.collection("UserAccount")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_register, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

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
                        //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
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
                val action: NavDirections = RegisterDirections.actionRegisterToMainView()
                view?.findNavController()?.navigate(action)
                Toast.makeText(context, "saved data", Toast.LENGTH_LONG).show()
            }

        } catch (e: Exception) {
            withContext(Dispatchers.Main) {
                Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
            }
        }
    }
}