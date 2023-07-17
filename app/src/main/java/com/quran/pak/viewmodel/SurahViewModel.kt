package com.quran.pak.viewmodel

import android.app.Application
import android.content.Context
import android.os.Handler
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.quran.pak.model.Surah
import com.quran.pak.model.SurahsData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException

class SurahViewModel(application: Application) : AndroidViewModel(application){
    private val surahListLiveData: MutableLiveData<List<Surah>> = MutableLiveData()


    fun loadSurahListFromAsset() : LiveData<List<Surah>> {
        viewModelScope.launch {
            val surahList = withContext(Dispatchers.IO) {
                loadSurahListFromAsset(getApplication(), "quran_meta.json")
            }
            Handler().postDelayed({
                surahListLiveData.value = surahList

            },100)
        }
        return surahListLiveData
    }
    private suspend fun loadSurahListFromAsset(context: Context, fileName: String): List<Surah>? {
        return withContext(Dispatchers.IO) {
            try {
                val inputStream = context.assets.open(fileName)
                val size = inputStream.available()
                val buffer = ByteArray(size)
                inputStream.read(buffer)
                inputStream.close()
                val json = String(buffer, Charsets.UTF_8)
                val gson = Gson()
                val surahsData = gson.fromJson(json, SurahsData::class.java)
                surahsData.suras
            } catch (ex: IOException) {
                ex.printStackTrace()
                null
            }
        }
    }

}