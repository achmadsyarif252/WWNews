package com.example.wwn.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.wnews.data.local.entity.BookmarkedNews
import com.example.wnews.data.local.entity.NewsModel
import com.example.wnews.data.local.helper.BookmarkDiffCallback
import com.example.wwn.databinding.ItemNewsBinding

class NewsAdapter(private val listNews: List<NewsModel>) :
    RecyclerView.Adapter<NewsAdapter.ViewHolder>() {
    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    class ViewHolder(val binding: ItemNewsBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.removeBookmark.visibility = View.INVISIBLE
        holder.binding.tvTitle.text = listNews[position].judul
        holder.binding.content.text = listNews[position].isi
        holder.itemView.setOnClickListener {
            onItemClickCallback.onItemClicked(listNews[position])
        }

    }

    override fun getItemCount() = listNews.size

    interface OnItemClickCallback {
        fun onItemClicked(data: NewsModel)
    }
}