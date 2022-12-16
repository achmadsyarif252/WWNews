package com.example.wnews.data.local.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "BookmarkedNews")
@Parcelize
class BookmarkedNews(
    @PrimaryKey
    @ColumnInfo(name = "judul")
    var judul: String = "",

    @ColumnInfo(name = "isi")
    var isi: String = "",

    @ColumnInfo(name = "author")
    var author: String = "",

    @ColumnInfo(name = "media")
    var media: String = "",

    @ColumnInfo(name = "publishedAt")
    var publishedAt: String = "",

    @ColumnInfo(name = "imageUrl")
    var imageUrl: String = ""


) : Parcelable
