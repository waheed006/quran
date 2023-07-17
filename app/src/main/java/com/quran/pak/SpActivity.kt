package com.quran.pak

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.quran.pak.util.arabicList
import com.quran.pak.util.englishList
import com.quran.pak.util.parseJSONFromAssets
import com.quran.pak.util.urduList

class SpActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_splash)

        populateData()
        bindSplash()
    }
    private fun bindSplash(){
        Handler(Looper.myLooper()!!).postDelayed({
            startActivity(Intent(applicationContext , HomeActivity::class.java))
        },6000)

    }

    private fun populateData(){
        val urdu = parseJSONFromAssets(applicationContext, "urdu_quran.json")
        val english = parseJSONFromAssets(applicationContext, "english_quran.json")
        val arabic = parseJSONFromAssets(applicationContext, "arabic_indopak.json")

        urduList = urdu
        englishList = english
        arabicList = arabic


    }

}