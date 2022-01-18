package com.tuwaiq.useraccount.registration

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.tuwaiq.useraccount.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class SignIn : Fragment() {

    private lateinit var doNotHaveAccount:TextView
    private lateinit var forgetPasswordText:TextView
    private lateinit var signInButton:Button
    private lateinit var enterYourEmail:TextInputEditText
    private lateinit var enterYourPass:TextInputEditText
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var sharedPreferences2: SharedPreferences
    private lateinit var progressBar: ProgressBar
    private var checkBoxValue = false
    private lateinit var rememberMe: CheckBox
    private val db = FirebaseFirestore.getInstance().collection("UserAccount")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_sign_in, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        doNotHaveAccount =view.findViewById(R.id.txt_dont_have_account)
        forgetPasswordText = view.findViewById(R.id.txt_forget_password)
        signInButton = view.findViewById(R.id.btnSignIn)
        enterYourEmail = view.findViewById(R.id.tiet_email_sign_in)
        enterYourPass = view.findViewById(R.id.tiet_password_sign_in)
        progressBar = view.findViewById(R.id.progressBarSingIn)


        rememberMe = view.findViewById(R.id.checkBox)
        sharedPreferences = this.requireActivity().getSharedPreferences("preference", Context.MODE_PRIVATE)
        sharedPreferences2 = this.requireActivity().getSharedPreferences("Profile", Context.MODE_PRIVATE)

        //check box action
        checkBoxValue = sharedPreferences.getBoolean("CHECKBOX", false)
        if (checkBoxValue) {
            findNavController().navigate(SignInDirections.actionSignInToMainView())
        }

        doNotHaveAccount.setOnClickListener {
            findNavController().navigate(SignInDirections.actionSignInToRegister())
        }
        forgetPasswordText.setOnClickListener {
            findNavController().navigate(SignInDirections.actionSignInToForgetPassword())
        }
        signInButton.setOnClickListener {
            signInButton.isClickable = false
            progressBar.isVisible = true
            signIn()
        }
    }

    private fun signIn() {
        val email: String = enterYourEmail.text.toString().trim()
        val password =enterYourPass.text.toString().trim()
        if(email.isNotEmpty() && password.isNotEmpty()) {

            //get the email and pass from the Authentication
            FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        checkInTheFireStore()
                    } else {
                        // if the registration is not successful then show error massage
                        Toast.makeText(context, "Please make sure the values are correct, or fill the fields",
                            Toast.LENGTH_LONG).show()
                        signInButton.isClickable =true
                        progressBar.isVisible = false
                    }
                }
        }else{
            Toast.makeText(context, "please fill fields", Toast.LENGTH_LONG)
                .show()
            signInButton.isClickable =true
            progressBar.isVisible = false
        }
    }

    //check in the fireStore
    private fun checkInTheFireStore()= CoroutineScope(Dispatchers.IO).launch{
        val uId =FirebaseAuth.getInstance().currentUser?.uid
        db.document("$uId")
            .get().addOnCompleteListener {
                if (it.result?.exists()!!){
                    getUserInfo()
                    findNavController().navigate(SignInDirections.actionSignInToMainView())
                    checkBox()
                }else{
                    Toast.makeText(context, "Please make sure the email or the password are correct", Toast.LENGTH_LONG)
                        .show()
                    signInButton.isClickable = true
                    progressBar.isVisible = false
                }
            }
    }

    //save check box
    private fun checkBox(){
        val checked: Boolean = rememberMe.isChecked
        sharedPreferences.edit()
        .putBoolean("CHECKBOX", checked)
        .apply()
    }

    //save user info to save it in sp
    private fun getUserInfo() = CoroutineScope(Dispatchers.IO).launch {
        val uId =FirebaseAuth.getInstance().currentUser?.uid
            db.document("$uId")
                .get().addOnCompleteListener {
                    if (it.result?.exists()!!) {
                        val name = it.result!!.getString("userName")
                        val userEmail = it.result!!.getString("emailAddress")
                        val userPhone = it.result!!.getString("number")

                        //to save the info in the sp
                        sharedPreferences2.edit()
                        .putString("spUserName",name.toString())
                        .putString("spEmail",userEmail.toString())
                        .putString("spPhoneNumber",userPhone.toString())
                        .apply()
                    }
                }
    }
}


