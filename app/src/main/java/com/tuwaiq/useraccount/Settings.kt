package com.tuwaiq.useraccount

import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreference
import com.tuwaiq.useraccount.save_settings.SaveData

class Settings : PreferenceFragmentCompat() {
    private lateinit var saveData: SaveData
    private var switch: SwitchPreference? = null

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {


        //sharedPreferences state look
        saveData = SaveData(requireContext())
        if (saveData.loadNightModeState() == true){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }else{
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
    }

}