package com.tuwaiq.useraccount.registration

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.NavDirections
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.tuwaiq.useraccount.R


class SignIn : Fragment() {

    private lateinit var dontHaveAccount:TextView
    private lateinit var forgetPasswordText:TextView
    private lateinit var signInButton:Button
    private lateinit var enterYourEmail:TextInputEditText
    private lateinit var enterYourPass:TextInputEditText

    private lateinit var sharedPreferences: SharedPreferences
    var checkBoxValue = false
    private lateinit var rememberMe: CheckBox

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_sign_in, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        dontHaveAccount =view.findViewById(R.id.txt_dont_have_account)
        forgetPasswordText = view.findViewById(R.id.txt_forget_password)
        signInButton = view.findViewById(R.id.btnSignIn)
        enterYourEmail = view.findViewById(R.id.tiet_email_sign_in)
        enterYourPass = view.findViewById(R.id.tiet_password_sign_in)

        //check box action
        rememberMe = view.findViewById(R.id.checkBox)
        sharedPreferences = this.requireActivity().getSharedPreferences("preference", Context.MODE_PRIVATE)
        checkBoxValue = sharedPreferences.getBoolean("CHECKBOX", false)
        if (checkBoxValue) {
            findNavController().navigate(R.id.action_signIn_to_mainView)
        }

        dontHaveAccount.setOnClickListener {
            val action: NavDirections = SignInDirections.actionSignInToRegister()
            view.findNavController().navigate(action)
        }
        forgetPasswordText.setOnClickListener {
            val action: NavDirections = SignInDirections.actionSignInToForgetPassword()
            view.findNavController().navigate(action)
        }
        signInButton.setOnClickListener {
            signIn()
        }
    }

    private fun signIn() {
        val email: String = enterYourEmail.text.toString().trim { it <= ' ' }
        val password =enterYourPass.text.toString().trim { it <= ' ' }
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
                    }
                }
        }else{
            Toast.makeText(context, "please enter all fields", Toast.LENGTH_LONG)
                .show()
        }
    }

    //check in the fireStore
    private fun checkInTheFireStore(){
        val uId =FirebaseAuth.getInstance().currentUser?.uid
        val db = FirebaseFirestore.getInstance()
        db.collection("UserAccount").document("$uId")
            .get().addOnCompleteListener {
                if (it.result?.exists()!!){
                    Toast.makeText(context, "Sign in successful", Toast.LENGTH_LONG)
                        .show()

                    val action: NavDirections = SignInDirections.actionSignInToMainView()
                    view?.findNavController()?.navigate(action)

                    checkBox()
                }else{
                    Toast.makeText(context, "Please make sure the values are correct", Toast.LENGTH_LONG)
                        .show()
                }
            }
    }


    private fun checkBox(){

        val emailPreference: String = enterYourEmail.text.toString()
        val passwordPreference: String = enterYourPass.text.toString()
        val checked: Boolean = rememberMe.isChecked
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.putString("EMAIL", emailPreference)
        editor.putString("PASSWORD", passwordPreference)
        editor.putBoolean("CHECKBOX", checked)
        editor.apply()
    }
}


