package com.quran.pak

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.quran.furqan.R
import com.quran.furqan.databinding.ActivityDetailBinding
import com.quran.pak.fragment.PageFragment
import com.quran.pak.fragment.VersesFragment

class DetailActivity : AppCompatActivity() {
    private var isSwtich = false
    private lateinit var binding: ActivityDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val surahName = intent.getStringExtra("SURAH_NAME")
        binding.name.text = surahName

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.fragContainer, PageFragment())
                .commit()
        }
        switchPage()
        event()
    }

    private fun event() {
        binding.imgBack.setOnClickListener {
            finish()
        }
    }

    private fun switchPage() {
        binding.list.setOnClickListener {

            isSwtich = if (isSwtich) {
                binding.list.setImageResource(R.drawable.ic_pages)
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragContainer, PageFragment())
                    .commit()
                false
            } else {
                binding.list.setImageResource(R.drawable.ic_list)
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragContainer, VersesFragment())
                    .commit()
                true
            }
        }
    }
}


