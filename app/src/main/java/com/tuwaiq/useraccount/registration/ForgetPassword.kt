package com.tuwaiq.useraccount.registration

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.tuwaiq.useraccount.R

class ForgetPassword : Fragment() {
    private lateinit var sendPassButton: Button
    private lateinit var enterToSendTheEmail : TextInputEditText
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_forget_password, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        sendPassButton = view.findViewById(R.id.btnSendThePass)
        enterToSendTheEmail = view.findViewById(R.id.tiet_email_forgotPass)

        sendPassButton.setOnClickListener {
            sendTheEmail()
        }
    }

    private fun sendTheEmail() {
        val email = enterToSendTheEmail.text.toString().trim()

        if (email.isEmpty()) {
            Toast.makeText(context, "Please enter your E-mail", Toast.LENGTH_SHORT).show()
        } else {
            FirebaseAuth.getInstance().sendPasswordResetEmail(email)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(
                            context, "E-mail send successful to reset your password",
                            Toast.LENGTH_LONG
                        ).show()
                        findNavController().navigate(R.id.signIn)
                    } else {
                        Toast.makeText(
                            context, "The email wasn't correct",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
        }
    }
}
