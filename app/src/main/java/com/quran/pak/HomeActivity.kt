package com.quran.pak

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.core.view.GravityCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.quran.pak.adapter.SurahAdapter
import com.quran.pak.databinding.ActivityHomeBinding
import com.quran.pak.model.Surah
import com.quran.pak.model.SurahsData
import com.quran.pak.util.arabicList
import com.quran.pak.util.englishList
import com.quran.pak.util.parseJSONFromAssets
import com.quran.pak.util.urduList
import com.quran.pak.viewmodel.SurahViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupRecyclerView()
        loadData()
        setupDrawer()
        setupEvent()

    }

    private fun setupEvent(){
        binding.imgSearch.setOnClickListener {
            binding.imgSearch.visibility = View.INVISIBLE
            binding.inputSearch.visibility = View.VISIBLE
        }
    }

    private fun setupRecyclerView() {
        binding.recyclerview.layoutManager = LinearLayoutManager(applicationContext)
    }

    private fun loadData() {
       val viewModel = ViewModelProvider(this)[SurahViewModel::class.java]
        viewModel.loadSurahListFromAsset().observe(this) {
            if (it != null){
                val adapter = SurahAdapter(it)
                adapter.updateData(it)
                binding.recyclerview.adapter = adapter

            }

        }
    }

    private fun setupDrawer(){
        binding.imgMenu.setOnClickListener {
            binding.drawerLayout.openDrawer(GravityCompat.START)
        }
        binding.navView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.home -> {
                    val intent = Intent(applicationContext , HomeActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
                    startActivity(intent)
                    binding.drawerLayout.closeDrawer(GravityCompat.START)
                    true
                }
                R.id.settings -> {
                    val intent = Intent(applicationContext , SettingActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
                    startActivity(intent)
                    binding.drawerLayout.closeDrawer(GravityCompat.START)
                    true
                }
                R.id.share -> {
                    share()
                    binding.drawerLayout.closeDrawer(GravityCompat.START)
                    true
                }
                R.id.rate -> {
                    rateUs()
                    binding.drawerLayout.closeDrawer(GravityCompat.START)

                    true
                }
                R.id.privacy -> {
                    openPrivacy()
                    binding.drawerLayout.closeDrawer(GravityCompat.START)
                    true
                }
                else -> false
            }
        }

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
}