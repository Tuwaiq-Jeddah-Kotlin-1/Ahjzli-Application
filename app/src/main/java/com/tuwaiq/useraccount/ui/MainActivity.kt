package com.tuwaiq.useraccount.ui

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_NO
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.tuwaiq.useraccount.R
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var navHostFragment :NavHostFragment
    private lateinit var navController :NavController

    @SuppressLint("CommitTransaction")
    override fun onCreate(savedInstanceState: Bundle?) {
        //lang
        val sharedPreferences = getSharedPreferences("Settings", Activity.MODE_PRIVATE)
            val language = sharedPreferences.getString("My_Lang", "")!!
            setLocal(language)

        //theme
            if (sharedPreferences.getBoolean("darkMode",true)) {
                AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_YES)
            }else{
                AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_NO)
            }
        super.onCreate(savedInstanceState)
            loadView()
        }

    @SuppressLint("ResourceType")
    private fun loadView() {
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()
        navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navController = navHostFragment.navController
        bottomNavController()
    }

    //bottom nav controller
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

    private fun setLocal(lang: String) {
        val locale = Locale(lang)
        Locale.setDefault(locale)
        val config = Configuration()
        config.locale = locale
        resources.updateConfiguration(config, resources.displayMetrics)
        val editor = getSharedPreferences("Settings", Context.MODE_PRIVATE).edit()
        editor.putString("My_Lang", lang)
        editor.apply()
    }
}