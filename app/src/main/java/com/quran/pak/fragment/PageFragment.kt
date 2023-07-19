package com.quran.pak.fragment

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.quran.pak.adapter.VersesAdapter
import com.quran.pak.databinding.FragmentPageBinding
import com.quran.pak.util.arabicList
import com.quran.pak.util.englishList
import com.quran.pak.util.surahIndex
import com.quran.pak.util.urduList


class PageFragment : Fragment() {
    private lateinit var binding: FragmentPageBinding
    private lateinit var sharedPref: SharedPreferences
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentPageBinding.inflate(inflater, container, false)
        sharedPref =
            requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        loadData()
        return binding.root
    }

    private fun loadData() {
        binding.recyclerview.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerview.setHasFixedSize(true)
        getData()
        val lastRead = sharedPref.getBoolean("LAST_READ", false)
        if (lastRead) {
            val lastPosition = sharedPref.getInt("LAST_POSITION", 0)
            binding.recyclerview.layoutManager?.scrollToPosition(lastPosition-1)
            val editor = sharedPref.edit()
            editor.putBoolean("LAST_READ", false)
            editor.apply()
        }
    }

    private fun getData() {

        val index = surahIndex - 1

        val adapter = VersesAdapter(
            true,
            arabicList.suras[index].ayas,
            urduList.suras[index].ayas,
            englishList.suras[index].ayas,
            requireContext()
        )
        binding.recyclerview.adapter = adapter


    }

}