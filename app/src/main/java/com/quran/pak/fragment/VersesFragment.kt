package com.quran.pak.fragment

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
    private lateinit var binding : FragmentVersesBinding
    private lateinit var viewModel : SurahListViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentVersesBinding.inflate(inflater , container , false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewModel()

        loadData()
    }
    private fun setupViewModel(){
        viewModel = ViewModelProvider(requireActivity())[SurahListViewModel::class.java]
    }

    private fun loadData(){
        getData()
    }
    private fun getData(){

        val index = surahIndex -1
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


    }
}