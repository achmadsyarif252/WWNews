package com.example.wnews.data.local.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class NewsModel(
    var judul: String = "",
    var isi: String = "",
    var author: String? = "",
    var media: String = "",
    var publishedAt: String = "",
    var imageUrl: String? = ""
) : Parcelable