package com.tuwaiq.useraccount

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.NavDirections
import androidx.navigation.findNavController


class Register : Fragment() {
    private lateinit var haveAccount: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_register, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        haveAccount =view.findViewById(R.id.txt_have_account)

        haveAccount.setOnClickListener {
            val action: NavDirections = RegisterDirections.actionRegisterToSignIn()
            view.findNavController().navigate(action)
        }

    }
}