package com.quran.pak.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.quran.pak.R
import com.quran.pak.adapter.VersesAdapter
import com.quran.pak.databinding.FragmentPageBinding
import com.quran.pak.util.arabicList
import com.quran.pak.util.englishList
import com.quran.pak.util.surahIndex
import com.quran.pak.util.urduList
import com.quran.pak.viewmodel.SurahListViewModel


class PageFragment : Fragment() {
    private lateinit var binding : FragmentPageBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentPageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadData()
    }


    private fun loadData(){
        binding.recyclerview.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerview.setHasFixedSize(true)
        getData()
    }
    private fun getData(){

        val index = surahIndex -1

        val adapter = VersesAdapter(true , arabicList.suras[index].ayas , urduList.suras[index].ayas , englishList.suras[index].ayas)
        binding.recyclerview.adapter = adapter


    }

}