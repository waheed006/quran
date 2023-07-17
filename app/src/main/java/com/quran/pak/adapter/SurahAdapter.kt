package com.quran.pak.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.quran.pak.DetailActivity
import com.quran.pak.R
import com.quran.pak.model.Surah
import com.quran.pak.util.surahIndex

class SurahAdapter(var list : List<Surah>) : RecyclerView.Adapter<SurahAdapter.SurahViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SurahViewHolder {
        return SurahViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.surah_item_row , parent , false))
    }

    override fun getItemCount(): Int {
       return list.size
    }

    override fun onBindViewHolder(holder: SurahViewHolder, position: Int) {
        holder.bindSurahNumber(list[position].index)
        holder.bindName(list[position].name.`in`)
        holder.bindEnglishName(list[position].translation.en)
    }

    fun updateData(newList: List<Surah>) {
        list = newList
    }


    inner class SurahViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) , View.OnClickListener{

        init {
            itemView.setOnClickListener(this)
        }

        fun bindSurahNumber(number : Int){
            val mCount = itemView.findViewById<TextView>(R.id.item_view_count)
            mCount.text = "$number"
        }
        fun bindName(name : String){
            val mName = itemView.findViewById<TextView>(R.id.item_row_name)
            mName.text = name
        }
        fun bindEnglishName(name : String){
            val mName = itemView.findViewById<TextView>(R.id.item_row_english_name)
            mName.text = name
        }

        override fun onClick(v: View) {
            val intent = Intent(v.context , DetailActivity::class.java)
            surahIndex = list[adapterPosition].index
            v.context.startActivity(intent)
        }
    }

}