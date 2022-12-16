package com.example.wwn.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.TextView.OnEditorActionListener
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wnews.ArticlesItem
import com.example.wnews.data.local.entity.NewsModel
import com.example.wwn.adapter.NewsAdapter
import com.example.wwn.databinding.FragmentHomeBinding
import com.example.wwn.ui.detail.DetailNews


class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val mainViewModel: MainViewModel = ViewModelProvider(this)[MainViewModel::class.java]
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val layoutManager = LinearLayoutManager(requireContext())
        binding.rvNews.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(requireContext(), layoutManager.orientation)
        binding.rvNews.addItemDecoration(itemDecoration)

        mainViewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }

        mainViewModel.listNews.observe(viewLifecycleOwner) {
            setListNews(it)
        }

        fun submit() {
            mainViewModel.getNews(binding.edSearch.text.toString())
        }
        binding.edSearch.onSubmit { submit() }


        return root

    }


    fun EditText.onSubmit(func: () -> Unit) {
        setOnEditorActionListener { _, actionId, _ ->

            if (actionId == EditorInfo.IME_ACTION_DONE) {
                func()
            }
            true
        }
    }

    private fun setListNews(articel: List<ArticlesItem>) {
        val listArticel = ArrayList<NewsModel>()
        for (art in articel) {
            val news = NewsModel()
            news.author = art.source.name
            news.isi = art.content
            news.judul = art.title
            news.publishedAt = art.publishedAt
            news.imageUrl = art.urlToImage
            news.media = art.description

            listArticel.add(news)
        }

        val adapter = NewsAdapter(listArticel)
        adapter.setOnItemClickCallback(object : NewsAdapter.OnItemClickCallback {
            override fun onItemClicked(data: NewsModel) {
                val i = Intent(requireContext(), DetailNews::class.java)
                DetailNews.STATUS = 0
                i.putExtra(DetailNews.EXTRA_NEWS, data)
                startActivity(i)
            }
        })
        binding.rvNews.adapter = adapter
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}