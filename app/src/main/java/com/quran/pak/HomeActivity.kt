package com.quran.pak

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.view.GravityCompat
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.quran.pak.adapter.SurahAdapter
import com.quran.pak.databinding.ActivityHomeBinding
import com.quran.pak.model.Surah
import com.quran.pak.viewmodel.SurahViewModel


class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private lateinit var adapter: SurahAdapter
    private var surahList = ArrayList<Surah>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.inputSearch.addTextChangedListener { editable ->
            filterByName(editable.toString())
        }
        setupRecyclerView()
        loadData()
        setupDrawer()
        setupEvent()
    }

    private fun setupEvent() {
        var isEditTextVisible = false
        binding.apply {
            imgSearch.setOnClickListener {
                isEditTextVisible = !isEditTextVisible
                if (isEditTextVisible) {
                    inputSearch.visibility = View.VISIBLE
                } else {
                    inputSearch.visibility = View.INVISIBLE
                }
            }
        }
    }

    private fun setupRecyclerView() {
        binding.recyclerview.layoutManager = LinearLayoutManager(applicationContext)
    }

    private fun loadData() {
        val viewModel = ViewModelProvider(this)[SurahViewModel::class.java]
        viewModel.loadSurahListFromAsset().observe(this) {
            if (it != null) {
                surahList.addAll(it)
                adapter = SurahAdapter(surahList)
                binding.recyclerview.adapter = adapter

            }
        }
    }

    private fun filterByName(text: String) {
        val filterByName = ArrayList<Surah>()
        surahList.filterTo(filterByName) {
            it.name.en.lowercase().contains(text.lowercase())
        }
        adapter.filterList(filterByName)
    }

    private fun setupDrawer() {
        binding.imgMenu.setOnClickListener {
            binding.drawerLayout.openDrawer(GravityCompat.START)
        }
        binding.navView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.home -> {
                    val intent = Intent(applicationContext, HomeActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
                    startActivity(intent)
                    binding.drawerLayout.closeDrawer(GravityCompat.START)
                    true
                }

                R.id.settings -> {
                    val intent = Intent(applicationContext, SettingActivity::class.java)
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
            val playStoreUri =
                Uri.parse("https://play.google.com/store/apps/details?id=$packageName")
            val playStoreIntent = Intent(Intent.ACTION_VIEW, playStoreUri)
            startActivity(playStoreIntent)
        }
    }

    private fun share() {
        val appPackageName = packageName
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.type = "text/plain"
        val shareText =
            "Check out this Quran app: https://play.google.com/store/apps/details?id=$appPackageName"
        shareIntent.putExtra(Intent.EXTRA_TEXT, shareText)
        startActivity(Intent.createChooser(shareIntent, "Share App via"))
    }
}