package com.tuwaiq.useraccount.save_settings

import android.content.Context
import android.content.SharedPreferences

class SaveData(context: Context) {
    private var sharedPreferences:SharedPreferences = context.getSharedPreferences("setting",Context.MODE_PRIVATE)

    //this methode will save the night mode state : true or false
    fun setDarkModeState(state:Boolean?){
        val editor = sharedPreferences.edit()
        editor.putBoolean("Dark",state!!)
        editor.apply()
    }

    //this method will load the night mode state
    fun loadNightModeState():Boolean?{
        val state = sharedPreferences.getBoolean("Dark",false)
        return (state)
    }
}