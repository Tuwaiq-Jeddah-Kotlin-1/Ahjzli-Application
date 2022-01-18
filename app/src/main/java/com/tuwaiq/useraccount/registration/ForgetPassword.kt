package com.tuwaiq.useraccount.registration

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.tuwaiq.useraccount.R

class ForgetPassword : Fragment() {
    private lateinit var sendPassButton: Button
    private lateinit var enterToSendTheEmail : TextInputEditText
    private lateinit var progressBar: ProgressBar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_forget_password, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        sendPassButton = view.findViewById(R.id.btnSendThePass)
        enterToSendTheEmail = view.findViewById(R.id.tiet_email_forgotPass)
        progressBar = view.findViewById(R.id.progressBarForget)

        sendPassButton.setOnClickListener {
            sendPassButton.isClickable = false
            progressBar.isVisible = true
            sendTheEmail()
        }
    }

    private fun sendTheEmail() {
        val email = enterToSendTheEmail.text.toString().trim()

        if (email.isEmpty()) {
            Toast.makeText(context, "Please enter your E-mail", Toast.LENGTH_SHORT).show()
            sendPassButton.isClickable = true
            progressBar.isVisible = false
        } else {
            FirebaseAuth.getInstance().sendPasswordResetEmail(email)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(
                            context, "E-mail send successful to reset your password",
                            Toast.LENGTH_LONG
                        ).show()
                        findNavController().navigate(ForgetPasswordDirections.actionForgetPasswordToSignIn())
                    } else {
                        Toast.makeText(
                            context, "The email wasn't correct",
                            Toast.LENGTH_LONG
                        ).show()
                        sendPassButton.isClickable = true
                        progressBar.isVisible = false
                    }
                }
        }
    }
}
