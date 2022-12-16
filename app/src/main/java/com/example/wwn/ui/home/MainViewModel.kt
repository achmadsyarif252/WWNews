package com.example.wwn.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.wnews.ArticlesItem
import com.example.wnews.NewsResponse
import com.example.wwn.data.remote.retrofit.ApiConfig
import com.example.wwn.BuildConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _listNews = MutableLiveData<List<ArticlesItem>>()
    val listNews: LiveData<List<ArticlesItem>> = _listNews

    init {
        getNews("Indonesia")
    }


    fun getNews(query: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getNewsAboutIndonesia(query, API_KEY)
        client.enqueue(object : Callback<NewsResponse> {
            override fun onFailure(call: Call<NewsResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }

            override fun onResponse(call: Call<NewsResponse>, response: Response<NewsResponse>) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _listNews.value = responseBody.articles
                    }
                } else {
                    Log.e(TAG, "onResponse: ${response.message()}")
                }
            }
        })
    }

    companion object {
        private const val TAG = "MainViewModel"
        private val API_KEY = BuildConfig.API_KEY
    }

}