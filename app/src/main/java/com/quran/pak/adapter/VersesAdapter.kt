package com.quran.pak.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.quran.pak.R
import com.quran.pak.detail.Ayah
import com.quran.pak.util.getEnglishLang
import com.quran.pak.util.getUrduLang

class VersesAdapter(
    private val isPage: Boolean,
    private val arabicList: List<Ayah>,
    private val urduList: List<Ayah>,
    private val englishList: List<Ayah>,
    private val context: Context,

    ) : RecyclerView.Adapter<VersesAdapter.VersesViewHolder>() {
    private val sharedPref =
        context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
    inner class VersesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindArabic(text: String) {
            val mText = itemView.findViewById<TextView>(R.id.item_view_arabic)
            mText.text = text
        }

        fun bindEnglish(text: String) {
            val mText = itemView.findViewById<TextView>(R.id.item_view_english)
            if (getEnglishLang(mText.context)) {
                mText.visibility = View.VISIBLE
            }
            val cleanedText = text.replace(Regex("<fn[^>]*>[^<]*</fn>"), "")
            mText.text = cleanedText
        }

        fun bindUrdu(text: String) {
            val mText = itemView.findViewById<TextView>(R.id.item_view_urdu)
            if (getUrduLang(mText.context)) {
                mText.visibility = View.VISIBLE
            }
            val cleanedText = text.replace(Regex("<fn[^>]*>[^<]*</fn>"), "")
            mText.text = cleanedText
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VersesViewHolder {
        return if (isPage) {
            VersesViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.page_item_row, parent, false)
            )

        } else {
            VersesViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.verses_item_row, parent, false)
            )

        }

    }

    override fun getItemCount(): Int {
        return arabicList.size
    }

    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(holder: VersesViewHolder, position: Int) {
        if (isPage) {
            if (position % 2 == 0) {
                holder.itemView.setBackgroundColor(ContextCompat.getColor(context, R.color.white))
            } else {
                holder.itemView.setBackgroundColor(
                    ContextCompat.getColor(
                        context,
                        R.color.light_gray
                    )
                )
            }
        }

        holder.bindArabic(arabicList[position].text + arabicList[position].end)
        holder.bindUrdu(urduList[position].text)
        holder.bindEnglish(englishList[position].text)

        val editor = sharedPref.edit()
        editor.putInt("LAST_POSITION", position)
        editor.apply()
    }
}