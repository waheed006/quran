package com.quran.pak.util

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.quran.pak.detail.Ayah
import com.quran.pak.detail.Quran
import com.quran.pak.detail.Surah
import java.io.IOException

var urduList : Quran = Quran(ArrayList())
var englishList : Quran = Quran(ArrayList())
var arabicList : Quran = Quran(ArrayList())


var surahIndex = 0
fun parseJSONFromAssets(context: Context, fileName: String): Quran {
    val jsonString = getJsonFromAssets(context, fileName)
    val gson = Gson()
    return gson.fromJson(jsonString, Quran::class.java)
}

fun getJsonFromAssets(context: Context, fileName: String): String {
    return context.assets.open(fileName).bufferedReader().use { it.readText() }
}
fun enableUrduLang (context: Context , enable : Boolean){
    context.getSharedPreferences("PREF" , Context.MODE_PRIVATE)
        .edit()
        .putBoolean("urdu" , enable)
        .apply()
}

fun getUrduLang(context: Context) : Boolean{
    return context.getSharedPreferences("PREF" , Context.MODE_PRIVATE)
        .getBoolean("urdu" , false)
}

fun enableEnglishLang (context: Context , enable : Boolean){
    context.getSharedPreferences("PREF" , Context.MODE_PRIVATE)
        .edit()
        .putBoolean("english" , enable)
        .apply()
}

fun getEnglishLang(context: Context) : Boolean{
    return context.getSharedPreferences("PREF" , Context.MODE_PRIVATE)
        .getBoolean("english" , false)
}




