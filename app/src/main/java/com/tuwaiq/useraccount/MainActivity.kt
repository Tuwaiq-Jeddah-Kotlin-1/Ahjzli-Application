package com.tuwaiq.useraccount

import android.annotation.SuppressLint
import android.app.Activity
import android.app.FragmentManager
import android.content.Context
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.tuwaiq.useraccount.rv_reservation.Reservation
import java.util.*

class MainActivity : AppCompatActivity() {
    private var backPressedTime = 0L
    lateinit var navHostFragment :NavHostFragment
    lateinit var navController :NavController
    @SuppressLint("CommitTransaction")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()
        navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navController = navHostFragment.navController

        val notification = intent.extras?.getString("titleTest")

        //Log.e("intent.extras",intent.extras.toString())
        Log.e("notification",notification.toString())
        if (notification != null) {
            Log.d("titleTest","im here")

            // Here we can decide what do to -- perhaps load other parameters from the intent extras such as IDs, etc
            if (notification == "messageTest") {
                Log.d("messageTest","im here")
                //val action:NavController = R.id.action_mainView_to_reservation
//                navController.navigate(R.id.action_mainView_to_reservation)
                Navigation.findNavController(this, R.id.fragmentContainerView)
                    .navigate(R.id.reservation)
            }
        }

        bottomNavController()


        //save and set language
        val sharedPreferences = getSharedPreferences("Settings", Activity.MODE_PRIVATE)
        val language = sharedPreferences.getString("My_Lang", "")!!
        setLocal(language)


    }

/*    override fun onBackPressed() {
        if (backPressedTime + 2500 > System.currentTimeMillis()){
            super.onBackPressed()
        }else{
            Toast.makeText(applicationContext, "Press back again to exit app", Toast.LENGTH_SHORT).show()
        }
        backPressedTime = System.currentTimeMillis()
    }*/
    private fun setLocal(lang: String) {
        val locale = Locale(lang)
        Locale.setDefault(locale)
        val config = Configuration()
        config.locale = locale
        this.resources?.updateConfiguration(config, this.resources?.displayMetrics)
        val editor = getSharedPreferences("Settings", Context.MODE_PRIVATE).edit()
        editor.putString("My_Lang", lang)
        editor.apply()
    }
    private fun bottomNavController(){
        val bottomNavView = findViewById<BottomNavigationView>(R.id.bottom_nav_view)
        bottomNavView.setupWithNavController(navController)
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.signIn -> {
                    bottomNavView.visibility = View.GONE
                }
                R.id.Splash -> {
                    bottomNavView.visibility = View.GONE
                }
                R.id.Register -> {
                    bottomNavView.visibility = View.GONE
                }
                R.id.forgetPassword -> {
                    bottomNavView.visibility = View.GONE
                }
                else -> {
                    bottomNavView.visibility = View.VISIBLE
                }
            }
        }
    }
}