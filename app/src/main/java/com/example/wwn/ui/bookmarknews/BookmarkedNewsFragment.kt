package com.example.wwn.ui.bookmarknews

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wnews.data.local.entity.BookmarkedNews
import com.example.wnews.data.local.helper.ViewModelFactory
import com.example.wwn.adapter.BookmarkedNewsAdapter
import com.example.wwn.adapter.NewsAdapter
import com.example.wwn.databinding.FragmentBookmarkedNewsBinding
import com.example.wwn.ui.detail.DetailNews

class BookmarkedNewsFragment : Fragment() {
    private var _binding: FragmentBookmarkedNewsBinding? = null
    private val binding get() = _binding!!
    private lateinit var bookmarkedAdapter: BookmarkedNewsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBookmarkedNewsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val bookMarkedViewModel = obtainViewModel(requireActivity())
        bookmarkedAdapter = BookmarkedNewsAdapter()

        bookMarkedViewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }


        bookMarkedViewModel.getAllNews().observe(viewLifecycleOwner) { listNews ->
            if (listNews != null) bookmarkedAdapter.setListNews(listNews)
            if (listNews.isEmpty()) {
                binding.infoBookmark.visibility = View.VISIBLE
            }
        }

        binding.rvNews.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = bookmarkedAdapter
        }

        bookmarkedAdapter.setOnItemClickCallback(object :
            BookmarkedNewsAdapter.OnItemClickCallback {
            override fun onItemClicked(data: BookmarkedNews) {
                bookMarkedViewModel.delete(data)
                Toast.makeText(requireContext(), "Berhasil Dihapus", Toast.LENGTH_SHORT).show()

            }
        })

        return root
    }


    private fun showLoading(state: Boolean) {
        binding.progressBar.visibility = if (state) View.VISIBLE else View.GONE
    }

    private fun obtainViewModel(activity: FragmentActivity): BookmarkedViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory)[BookmarkedViewModel::class.java]
    }

}