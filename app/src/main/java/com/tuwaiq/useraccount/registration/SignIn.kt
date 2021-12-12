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
import com.tuwaiq.useraccount.R


class SignIn : Fragment() {

    private lateinit var dontHaveAccount:TextView
    private lateinit var forgetPasswordText:TextView
    private lateinit var signInButton:Button
    private lateinit var enterYourEmail:TextInputEditText
    private lateinit var enterYourPass:TextInputEditText

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
            //save to the Authentication
            FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(context, "Sign in successful", Toast.LENGTH_LONG)
                            .show()

                        val action: NavDirections = SignInDirections.actionSignInToMainView()
                        view?.findNavController()?.navigate(action)

                    } else {
                        // if the registration is not successful then show error massage
                        Toast.makeText(
                            context, "Please make sure the values are correct, or fill the fields",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
        }else{
            Toast.makeText(context, "please enter all fields", Toast.LENGTH_LONG)
                .show()
        }
    }

}


