package com.example.wwn.ui.bookmarknews

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.wnews.data.local.BookmarkedNewsRepository
import com.example.wnews.data.local.entity.BookmarkedNews

class BookmarkedViewModel(application: Application) : ViewModel() {
    private val mBookmarkedNewsRepository: BookmarkedNewsRepository =
        BookmarkedNewsRepository(application)
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getAllNews(): LiveData<List<BookmarkedNews>> {
        _isLoading.value = true
        val news = mBookmarkedNewsRepository.getALlNews()
        _isLoading.value = false
        return news
    }

    fun isBookmarked(judul: String?): LiveData<Boolean> {
        return mBookmarkedNewsRepository.isBookmaked(judul)
    }

    fun insert(news: BookmarkedNews) {
        mBookmarkedNewsRepository.insert(news)
    }

    fun delete(news: BookmarkedNews) {
        mBookmarkedNewsRepository.delete(news)
    }
}