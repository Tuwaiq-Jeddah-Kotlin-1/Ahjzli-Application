package com.tuwaiq.useraccount.ui

import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import com.airbnb.lottie.LottieAnimationView
import com.tuwaiq.useraccount.R

class Splash : Fragment() {

    private lateinit var lottie: LottieAnimationView
    private lateinit var logoTextView:TextView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        lottie =view.findViewById(R.id.lottieAnimationView)
        logoTextView = view.findViewById(R.id.txt_logo)

        lottie.startAnimation(AnimationUtils.loadAnimation(context, R.anim.logo_img))
        logoTextView.startAnimation(AnimationUtils.loadAnimation(context, R.anim.text_logo))
        Handler().postDelayed({
            findNavController().navigate(SplashDirections.actionSplashToSignIn())
        }, 5000)
    }
}