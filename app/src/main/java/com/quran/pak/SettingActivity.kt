package com.quran.pak

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.quran.pak.databinding.ActivitySettingBinding
import com.quran.pak.util.enableEnglishLang
import com.quran.pak.util.enableUrduLang
import com.quran.pak.util.getEnglishLang
import com.quran.pak.util.getUrduLang

class SettingActivity : AppCompatActivity() {
    private lateinit var binding : ActivitySettingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        defaultState()
        event()
    }
    private fun event(){
        binding.checkBoxUrdu.setOnCheckedChangeListener { b, isChecked ->
            if (isChecked){
                enableUrduLang(applicationContext , true)
            }else{
                enableUrduLang(applicationContext , false)
            }
        }
        binding.checkBoxEnglish.setOnCheckedChangeListener { b, isChecked ->
            if (isChecked){
                enableEnglishLang(applicationContext , true)
            }else{
                enableEnglishLang(applicationContext , false)
            }
        }


        binding.share.setOnClickListener { share() }
        binding.rate.setOnClickListener { rateUs() }
        binding.privacy.setOnClickListener { openPrivacy() }


    }

    private fun openPrivacy() {
        val webIntent = Intent(Intent.ACTION_VIEW)
        webIntent.data = Uri.parse(getString(R.string.privacy_url))
        startActivity(webIntent)
    }

    private fun rateUs() {
        val packageName = packageName
        val marketUri = Uri.parse("market://details?id=$packageName")
        val marketIntent = Intent(Intent.ACTION_VIEW, marketUri)
        marketIntent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY or Intent.FLAG_ACTIVITY_NEW_DOCUMENT or Intent.FLAG_ACTIVITY_MULTIPLE_TASK)

        try {
            startActivity(marketIntent)
        } catch (e: ActivityNotFoundException) {
            val playStoreUri = Uri.parse("https://play.google.com/store/apps/details?id=$packageName")
            val playStoreIntent = Intent(Intent.ACTION_VIEW, playStoreUri)
            startActivity(playStoreIntent)
        }
    }

    private fun share() {
        val appPackageName = packageName
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.type = "text/plain"
        val shareText = "Check out this Quran app: https://play.google.com/store/apps/details?id=$appPackageName"
        shareIntent.putExtra(Intent.EXTRA_TEXT, shareText)
        startActivity(Intent.createChooser(shareIntent, "Share App via"))
    }

    private fun defaultState(){
        binding.checkBoxUrdu.isChecked = getUrduLang(applicationContext)
        binding.checkBoxEnglish.isChecked = getEnglishLang(applicationContext)

    }
}