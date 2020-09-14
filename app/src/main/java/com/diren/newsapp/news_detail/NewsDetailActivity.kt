package com.diren.newsapp.news_detail

import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.room.Room
import com.diren.newsapp.R
import com.diren.newsapp.ResultWrapper
import com.diren.newsapp.db.AppDatabase
import com.diren.newsapp.isVisible
import com.diren.newsapp.model.News
import com.diren.newsapp.model.NewsResponse
import com.diren.newsapp.nonEmptyStringOrNull
import kotlinx.android.synthetic.main.activity_detail_news.*
import kotlinx.android.synthetic.main.list_news.*
import kotlinx.android.synthetic.main.list_news.view.*
import kotlinx.android.synthetic.main.list_news.view.txt_author
import kotlinx.android.synthetic.main.list_news.view.txt_description
import kotlinx.android.synthetic.main.list_news.view.txt_reading_list
import kotlinx.android.synthetic.main.list_news.view.txt_title


class NewsDetailActivity : AppCompatActivity() {

    private var vm: NewsDetailVM? = null
    private var detailAdapter: NewsDetailAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_news)

            init()

    }

    private fun init() {
        vm = ViewModelProvider.AndroidViewModelFactory(this.application).create(NewsDetailVM::class.java)
        val intent = intent
        val source_id = intent.getStringExtra("source_ıd")
        Log.d("SOURCEID",source_id)
        vm?.newsLiveData?.observe(this, Observer { result ->

            handleLoading(result.isLoading)

            when (result.state) {
                ResultWrapper.State.SUCCESS -> {
                    handleSuccess(result.data)
                }

                ResultWrapper.State.FAILURE -> {
                    handleError(result.error)
                }
            }
        })

       searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                vm?.fetchNewsData("",source_id)
                return true
            }
        })

        vm?.fetchNewsData("",source_id)
        Log.d("SOURCEID",source_id)
    }

    private fun handleLoading(isLoading: Boolean) {
        if (isLoading) {
            txt_info.isVisible = true
            txt_info.text = "Loading"
        } else {
            txt_info.isVisible = false
        }
    }

    private fun handleSuccess(data: NewsResponse?) {
        if (data?.articles?.isNotEmpty() == true) {
            if (detailAdapter == null) {
                detailAdapter = NewsDetailAdapter(data.articles)
                recyclewView.adapter = detailAdapter
                if (recyclewView.itemDecorationCount == 0) {
                    recyclewView.addItemDecoration(
                        DividerItemDecoration(
                            this,
                            LinearLayout.VERTICAL
                        )
                    )
                }
            } else {
                detailAdapter?.updateData(data.articles)
            }
        } else {
            txt_info.isVisible = true
            txt_info.text = "No Search Results"
        }
    }

    private fun handleError(error: Throwable?) {
        val errorMessage = error?.message.nonEmptyStringOrNull() ?: "Something went wrong."
        txt_info.text = errorMessage
        txt_info.isVisible = true
    }
}
