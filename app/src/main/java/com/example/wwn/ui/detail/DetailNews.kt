package com.example.wwn.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.wnews.data.local.entity.BookmarkedNews
import com.example.wnews.data.local.entity.NewsModel
import com.example.wnews.data.local.helper.ViewModelFactory
import com.example.wwn.R
import com.example.wwn.databinding.ActivityDetailNewsBinding
import com.example.wwn.ui.bookmarknews.BookmarkedViewModel

class DetailNews : AppCompatActivity() {
    private var newsItem: BookmarkedNews? = null
    private lateinit var binding: ActivityDetailNewsBinding
    private lateinit var bookmarkedViewModel: BookmarkedViewModel
    var isBookmarked = false
    private lateinit var menuItem: Menu;
    var news: NewsModel? = null
    var bookmarkedNews: BookmarkedNews? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailNewsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        invalidateMenu()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        bookmarkedViewModel = obtainViewModel(this@DetailNews)

        if (STATUS == 0) {
            news = intent.getParcelableExtra<NewsModel>(EXTRA_NEWS)
            bookmarkedViewModel.isBookmarked(news?.judul).observe(this) {
                isBookmarked = it
            }
        }
        if (STATUS == 1) {
            bookmarkedNews = intent.getParcelableExtra<BookmarkedNews>(EXTRA_NEWS)
            bookmarkedNews?.let { populateBookmark(it) }
            bookmarkedViewModel.isBookmarked(bookmarkedNews?.judul).observe(this) {
                isBookmarked = it
            }
        }

        news?.let { populateNewsDetail(it) }
    }

    private fun obtainViewModel(activity: AppCompatActivity): BookmarkedViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory)[BookmarkedViewModel::class.java]
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        this.menuItem = menu!!
        menuItem.getItem(1).icon = ContextCompat.getDrawable(
            this,
            if (isBookmarked) R.drawable.ic_baseline_bookmarks_24 else R.drawable.ic_baseline_bookmark_border_24
        )
        return true
    }

    private fun populateNewsDetail(news: NewsModel) {
        binding.title.text = news.judul
        binding.author.text = news.author
        var setBreak = 3
        var content = ""
        for (i in 1..20) {
            content += news.isi
            if (i == setBreak) {
                content += "\n\n"
                setBreak += 3
            }
        }
        binding.content.text = content
        binding.publishedAt.text = news.publishedAt
        Glide.with(this@DetailNews)
            .load(news.imageUrl)
            .into(binding.ivPicture)

        newsItem = news.author?.let {
            BookmarkedNews(
                news.judul,
                news.isi,
                it,
                news.media,
                news.publishedAt,
                news.imageUrl!!
            )
        }
    }

    private fun populateBookmark(news: BookmarkedNews) {
        binding.title.text = news.judul
        binding.author.text = news.author
        var setBreak = 3
        var content = ""
        for (i in 1..20) {
            content += news.isi
            if (i == setBreak) {
                content += "\n\n"
                setBreak += 3
            }
        }
        binding.content.text = content
        binding.publishedAt.text = news.publishedAt
        Glide.with(this@DetailNews)
            .load(news.imageUrl)
            .into(binding.ivPicture)

    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.bookmark -> {
                newsItem?.let { bookmarkedViewModel.insert(it) }
                menuItem.getItem(1).icon = ContextCompat.getDrawable(
                    this,
                    if (!isBookmarked) R.drawable.ic_baseline_bookmarks_24 else R.drawable.ic_baseline_bookmark_border_24
                )
            }
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }

        return true
    }

    companion object {
        const val EXTRA_NEWS = "EXTRA_NEWS"
        var STATUS = 0
    }
}