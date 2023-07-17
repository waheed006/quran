package com.quran.pak.model

data class Surah(
    val index: Int,
    val count: Int,
    val order: Int,
    val pages: String,
    val rukus: Int,
    val start: Int,
    val type: String,
    val translation: Translation,
    val name: Name,
    val tags: String
)







