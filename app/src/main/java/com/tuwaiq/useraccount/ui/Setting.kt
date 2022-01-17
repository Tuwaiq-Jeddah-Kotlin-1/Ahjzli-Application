package com.tuwaiq.useraccount.ui

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Switch
import android.widget.TextView
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_NO
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES
import androidx.core.app.ActivityCompat.recreate
import com.tuwaiq.useraccount.R
import java.util.*


class Setting : Fragment() {
    private lateinit var languageTextView:TextView
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    private lateinit var switch: Switch

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_setting, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        languageTextView = view.findViewById(R.id.language)
        switch = view.findViewById(R.id.swMode)
        val editor = this.requireActivity().getSharedPreferences("Settings", Context.MODE_PRIVATE)

        //theme
        switch.isChecked = editor.getBoolean("darkMode",false)

        switch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_NO)
            }
            editor.edit()
            .putBoolean("darkMode",isChecked)
                .apply()
        }

        languageTextView.setOnClickListener {
            showChangeLanguage()
        }


    }
    //show change language
    @SuppressLint("SetTextI18n")
    private fun showChangeLanguage(){
        val languageList = arrayOf("English","العربية")
        var current = 0
        val sharedPreferences = this.requireActivity().getSharedPreferences("Settings", Activity.MODE_PRIVATE)
        val language = sharedPreferences.getString("My_Lang", "")!!
        if (language == "en"){
            current =0
        }else if (language == "ar"){
            current=1
        }
        val mBuilder = AlertDialog.Builder(this.context)
        mBuilder.setTitle(getString(R.string.chose_language))
        mBuilder.setSingleChoiceItems(languageList,current){dialog, which ->
            if (current!=which) {
                if (which == 0) {
                    setLocate("en")
                } else if (which == 1) {
                    setLocate("ar")
                }
                recreate(context as Activity)
            }
            dialog.dismiss()
        }
        val mDialog = mBuilder.create()
        mDialog.show()
    }
    //save and set language
    private fun setLocate(lang: String) {
        val locale = Locale(lang)
        Locale.setDefault(locale)
        val config = Configuration()
        config.locale = locale
        context?.resources?.updateConfiguration(config, context?.resources?.displayMetrics)
        val editor = this.requireActivity().getSharedPreferences("Settings", Context.MODE_PRIVATE).edit()
        editor.putString("My_Lang", lang)
        editor.apply()
    }
}