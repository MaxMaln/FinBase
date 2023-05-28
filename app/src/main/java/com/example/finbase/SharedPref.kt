package com.example.finbase

import android.content.Context
import android.content.SharedPreferences

class SharedPref(context: Context) {

    private var mySharedPref: SharedPreferences

    init {
        mySharedPref = context.getSharedPreferences("filename",Context.MODE_PRIVATE)
    }

    fun setNightModeState(state:Boolean?){
        val editor: SharedPreferences.Editor? = mySharedPref.edit()
        editor?.putBoolean("Night Mode",state!!)
        editor?.apply()

    }

    fun loadNightModeState(): Boolean {
        return mySharedPref.getBoolean("Night Mode", false)
    }
}
