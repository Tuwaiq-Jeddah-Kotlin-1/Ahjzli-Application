package com.tuwaiq.useraccount

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import androidx.navigation.findNavController


class SignIn : Fragment() {

    private lateinit var dontHaveAccount:TextView
    private lateinit var forgetPasswordText:TextView
    private lateinit var signInButton:Button

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

        dontHaveAccount.setOnClickListener {
            val action: NavDirections = SignInDirections.actionSignInToRegister()
            view.findNavController().navigate(action)
        }
        forgetPasswordText.setOnClickListener {
            val action: NavDirections = SignInDirections.actionSignInToForgetPassword()
            view.findNavController().navigate(action)
        }
        signInButton.setOnClickListener {
            val action: NavDirections = SignInDirections.actionSignInToMainView()
            view.findNavController().navigate(action)
        }

    }
}