package com.example.wnews.data.local.helper

import androidx.recyclerview.widget.DiffUtil
import com.example.wnews.data.local.entity.BookmarkedNews

class BookmarkDiffCallback(
    private val mOldBookmarkList: List<BookmarkedNews>,
    private val mNewBookmarkList: List<BookmarkedNews>

) : DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return mOldBookmarkList.size
    }

    override fun getNewListSize(): Int {
        return mNewBookmarkList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return mOldBookmarkList[oldItemPosition].judul == mNewBookmarkList[newItemPosition].judul
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldBookmarked = mOldBookmarkList[oldItemPosition]
        val newBookmarked = mNewBookmarkList[newItemPosition]
        return oldBookmarked.judul == newBookmarked.judul && oldBookmarked.isi == newBookmarked.isi
    }

}