package com.example.wnews.data.local

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.wnews.data.local.entity.BookmarkedNews
import com.example.wnews.data.local.room.BookmakedNewsDatabase
import com.example.wwn.data.local.room.BookmarkedNewsDao
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class BookmarkedNewsRepository(application: Application) {
    private val mBookmarkedNewsDao: BookmarkedNewsDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = BookmakedNewsDatabase.getDatabase(application)
        mBookmarkedNewsDao = db.bookmarkedDao()
    }

    fun isBookmaked(judul: String?): LiveData<Boolean> = mBookmarkedNewsDao.isBookmarked(judul)


    fun getALlNews(): LiveData<List<BookmarkedNews>> = mBookmarkedNewsDao.getAllNews()

    fun insert(news: BookmarkedNews) {
        executorService.execute { mBookmarkedNewsDao.insert(news) }
    }

    fun delete(news: BookmarkedNews) {
        executorService.execute { mBookmarkedNewsDao.delete(news) }
    }
}