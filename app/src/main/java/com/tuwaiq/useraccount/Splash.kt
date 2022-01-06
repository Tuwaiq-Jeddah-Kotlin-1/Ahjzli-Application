package com.tuwaiq.useraccount

import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.TextView
import androidx.navigation.NavDirections
import androidx.navigation.findNavController
import com.airbnb.lottie.LottieAnimationView

class Splash : Fragment() {

    private lateinit var lottie: LottieAnimationView
    private lateinit var logoTextView:TextView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_splash, container, false)
/*        val main =  requireActivity() as MainActivity
        main.settings()*/
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        lottie =view.findViewById(R.id.lottieAnimationView)
        logoTextView = view.findViewById(R.id.txt_logo)


        lottie.startAnimation(AnimationUtils.loadAnimation(context, R.anim.logo_img))
        logoTextView.startAnimation(AnimationUtils.loadAnimation(context, R.anim.text_logo))
        Handler().postDelayed({
            val action: NavDirections = SplashDirections.actionSplashToSignIn()
            view.findNavController().navigate(action)
        }, 5000)
    }
}