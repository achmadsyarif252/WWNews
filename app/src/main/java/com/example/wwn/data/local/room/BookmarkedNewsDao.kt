package com.example.wwn.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.wnews.data.local.entity.BookmarkedNews
import com.example.wnews.data.local.entity.NewsModel

@Dao
interface BookmarkedNewsDao {
    @Query("SELECT * FROM BookmarkedNews ORDER BY judul ASC")
    fun getAllNews(): LiveData<List<BookmarkedNews>>


    @Query("SELECT EXISTS(SELECT * FROM BookmarkedNews where judul = :judul)")
    fun isBookmarked(judul: String?): LiveData<Boolean>

    @Delete
    fun delete(news: BookmarkedNews)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(news: BookmarkedNews)
}