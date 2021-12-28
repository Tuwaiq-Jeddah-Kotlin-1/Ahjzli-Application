package com.tuwaiq.useraccount

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
import androidx.core.app.ActivityCompat.recreate
import java.util.*


class Setting : Fragment() {
    private lateinit var languageTextView:TextView
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    private lateinit var switch: Switch
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_setting, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        languageTextView = view.findViewById(R.id.language)
        switch = view.findViewById(R.id.swMode)

        switch.setOnCheckedChangeListener{_, isChecked ->
            val mode: Boolean = isChecked

        }

        languageTextView.setOnClickListener {
            showChangeLanguage()
        }


    }
    //show change language
    private fun showChangeLanguage(){
        val languageList = arrayOf("English","العربية")
        val mBuilder = AlertDialog.Builder(this.context)
        mBuilder.setTitle("Chose Language")
        mBuilder.setSingleChoiceItems(languageList,-1){dialog, which ->
            if (which == 0) {
                setLocate("en")
            }else if (which == 1){
                setLocate("ar")
            }
            recreate(context as Activity)
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