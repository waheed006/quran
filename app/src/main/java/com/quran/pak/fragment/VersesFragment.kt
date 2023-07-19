package com.quran.pak.fragment

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.quran.pak.adapter.VersesAdapter
import com.quran.pak.databinding.FragmentVersesBinding
import com.quran.pak.util.arabicList
import com.quran.pak.util.englishList
import com.quran.pak.util.surahIndex
import com.quran.pak.util.urduList
import com.quran.pak.viewmodel.SurahListViewModel


class VersesFragment : Fragment() {
    private lateinit var binding: FragmentVersesBinding
    private lateinit var viewModel: SurahListViewModel
    private lateinit var sharedPref: SharedPreferences
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentVersesBinding.inflate(inflater, container, false)
        sharedPref =
            requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        setupViewModel()
        loadData()
        return binding.root
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(requireActivity())[SurahListViewModel::class.java]
    }

    private fun loadData() {
        getData()
    }

    private fun getData() {

        val index = surahIndex - 1
        /*val urdu = parseJSONFromAssets(requireContext(), "urdu_quran.json")
        val english = parseJSONFromAssets(requireContext(), "english_quran.json")
        val arabic = parseJSONFromAssets(requireContext(), "arabic_indopak.json")
*/

        val adapter = VersesAdapter(
            false,
            arabicList.suras[index].ayas,
            urduList.suras[index].ayas,
            englishList.suras[index].ayas,
            requireContext()
        )

        binding.viewpager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        binding.viewpager.adapter = adapter
        val lastRead = sharedPref.getBoolean("LAST_READ", false)
        if (lastRead) {
            binding.viewpager.currentItem = sharedPref.getInt("LAST_POSITION", 0)-1
            val editor = sharedPref.edit()
            editor.putBoolean("LAST_READ", false)
            editor.apply()
        }
    }
}