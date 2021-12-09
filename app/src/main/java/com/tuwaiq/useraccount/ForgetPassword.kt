package com.tuwaiq.useraccount

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.NavDirections
import androidx.navigation.findNavController

class ForgetPassword : Fragment() {
    private lateinit var sendPassButton: Button
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_forget_password, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        sendPassButton = view.findViewById(R.id.btnSendThePass)

        sendPassButton.setOnClickListener {
            val action: NavDirections = ForgetPasswordDirections.actionForgetPasswordToSignIn()
            view.findNavController().navigate(action)
        }
    }
}