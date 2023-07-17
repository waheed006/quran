package com.quran.pak.viewmodel

import android.app.Application
import android.content.Context
import android.os.Handler
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.quran.pak.detail.Ayah
import com.quran.pak.detail.Surah
import com.quran.pak.util.parseJSONFromAssets
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class SurahListViewModel(application: Application) : AndroidViewModel(application) {

    val mutableList : MutableLiveData<List<Surah>> = MutableLiveData()
    var list : List<Surah> = ArrayList()

    fun getArabicQuran(context: Context , index : Int) : LiveData<List<Surah>>{
        loadData(context , index)
        Handler().postDelayed({
            mutableList.value = list

        },300)

        return mutableList
    }
    fun loadData(context : Context, index: Int) : LiveData<List<Surah>>{
        GlobalScope.launch(Dispatchers.IO) {
            val quran = parseJSONFromAssets(context, "arabic_indopak.json")
            list = quran.suras
        }
        return mutableList
    }

}