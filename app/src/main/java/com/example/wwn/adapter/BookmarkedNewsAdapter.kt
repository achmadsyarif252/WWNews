package com.example.wwn.adapter


import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.wnews.data.local.entity.BookmarkedNews
import com.example.wnews.data.local.entity.NewsModel
import com.example.wnews.data.local.helper.BookmarkDiffCallback
import com.example.wwn.databinding.ItemNewsBinding
import com.example.wwn.ui.detail.DetailNews

class BookmarkedNewsAdapter :
    RecyclerView.Adapter<BookmarkedNewsAdapter.ViewHolder>() {
    private val listNew = ArrayList<BookmarkedNews>()
    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    inner class ViewHolder(val binding: ItemNewsBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(news: BookmarkedNews) {
            binding.removeBookmark.visibility = View.VISIBLE
            binding.tvTitle.text = news.judul
            binding.content.text = news.isi
            binding.cVitemNews.setOnClickListener {
                val intent = Intent(it.context, DetailNews::class.java)
                DetailNews.STATUS = 1
                intent.putExtra(DetailNews.EXTRA_NEWS, news)
                it.context.startActivity(intent)
            }
            binding.removeBookmark.setOnClickListener {
                onItemClickCallback.onItemClicked(news)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    fun setListNews(listNews: List<BookmarkedNews>) {
        val diffCallback = BookmarkDiffCallback(this.listNew, listNews)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.listNew.clear()
        this.listNew.addAll(listNews)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listNew[position])
    }

    override fun getItemCount() = listNew.size

    interface OnItemClickCallback {
        fun onItemClicked(data: BookmarkedNews)
    }
}