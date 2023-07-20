package com.quran.pak

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.quran.furqan.databinding.ActivityHomeBinding
import com.quran.pak.adapter.SurahAdapter
import com.quran.pak.model.Surah
import com.quran.pak.util.surahIndex
import com.quran.pak.viewmodel.SurahViewModel

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private lateinit var adapter: SurahAdapter
    private var surahList = ArrayList<Surah>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val sharedPref: SharedPreferences =
            this.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putBoolean("LAST_READ", false)
        editor.apply()
        var isEditTextVisible = false

        binding.apply {
            recyclerview.layoutManager = LinearLayoutManager(applicationContext)

            val viewModel = ViewModelProvider(this@HomeActivity)[SurahViewModel::class.java]
            viewModel.loadSurahListFromAsset().observe(this@HomeActivity) {
                if (it != null) {
                    surahList.addAll(it)
                    adapter = SurahAdapter(surahList, this@HomeActivity)
                    binding.recyclerview.adapter = adapter

                }
            }

            inputSearch.addTextChangedListener { editable ->
                filterByName(editable.toString())
            }

            imgSearch.setOnClickListener {

                isEditTextVisible = !isEditTextVisible
                if (isEditTextVisible) {
                    inputSearch.visibility = View.VISIBLE
                } else {
                    inputSearch.visibility = View.INVISIBLE
                }
            }

            lastRead.setOnClickListener {
                val lastIndex = sharedPref.getInt("surahIndex", 1)
                editor.putBoolean("LAST_READ", true)
                editor.apply()
                val intent = Intent(this@HomeActivity, DetailActivity::class.java)
                intent.putExtra("SURAH_NAME", surahList[lastIndex-1].name.ar)
                surahIndex = lastIndex
                startActivity(intent)
            }

            btSetting.setOnClickListener {
                val intent = Intent(this@HomeActivity, SettingActivity::class.java)
                startActivity(intent)
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
}