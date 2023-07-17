package com.quran.pak

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.MenuItem
import android.widget.ImageView
import androidx.core.view.GravityCompat
import com.google.android.material.navigation.NavigationView.OnNavigationItemSelectedListener
import com.quran.pak.databinding.ActivityDetailBinding
import com.quran.pak.fragment.PageFragment
import com.quran.pak.fragment.VersesFragment

class DetailActivity : AppCompatActivity() {
    private var isSwtich = false
    private lateinit var binding : ActivityDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if(savedInstanceState == null){
            supportFragmentManager.beginTransaction()
                .add(R.id.fragContainer , VersesFragment())
                .commit()
        }

        switchPage()
        event()
    }
    private fun event(){
        binding.imgBack.setOnClickListener {
            finish()
        }
    }

    private fun switchPage(){
        binding.verses.setOnClickListener {

            supportFragmentManager.beginTransaction()
                .replace(R.id.fragContainer , VersesFragment())
                .commit()


          /*  if (isSwtich){
                binding.pageSwitch.setImageResource(R.drawable.ic_list)
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragContainer , PageFragment())
                    .commit()
                isSwtich = false
            }else{
                binding.pageSwitch.setImageResource(R.drawable.ic_page)
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragContainer , VersesFragment())
                    .commit()
                isSwtich = true
            }*/
        }
        binding.list.setOnClickListener {

            supportFragmentManager.beginTransaction()
                .replace(R.id.fragContainer , PageFragment())
                .commit()


            /*  if (isSwtich){
                  binding.pageSwitch.setImageResource(R.drawable.ic_list)
                  supportFragmentManager.beginTransaction()
                      .replace(R.id.fragContainer , PageFragment())
                      .commit()
                  isSwtich = false
              }else{
                  binding.pageSwitch.setImageResource(R.drawable.ic_page)
                  supportFragmentManager.beginTransaction()
                      .replace(R.id.fragContainer , VersesFragment())
                      .commit()
                  isSwtich = true
              }*/
        }


    }


}