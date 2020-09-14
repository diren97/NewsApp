package com.diren.newsapp.news

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import com.diren.newsapp.R
import com.diren.newsapp.ResultWrapper
import com.diren.newsapp.isVisible
import com.diren.newsapp.model.NewsResponse
import com.diren.newsapp.nonEmptyStringOrNull
import kotlinx.android.synthetic.main.activity_detail_news.*

class NewsActivity : AppCompatActivity() {

    private var vm: NewsVM? = null
    private var adapter: NewsSourceAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news)
        init()
    }

    private fun init() {
        vm = ViewModelProvider.AndroidViewModelFactory(this.application).create(NewsVM::class.java)

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
                vm?.fetchNewsData(newText)
                return true
            }
        })

        vm?.fetchNewsData()
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
        data?.sources?.forEach { s ->
            Log.d("dddd", "s -> ${s.description}")
        }
        if (data?.sources?.isNotEmpty() == true) {
            Log.d("dddd", "satir1")
            if (adapter == null) {
                Log.d("dddd", "satir2")
                adapter = NewsSourceAdapter(data.sources)
                recyclewView.adapter = adapter
                if (recyclewView.itemDecorationCount == 0) {
                    recyclewView.addItemDecoration(
                        DividerItemDecoration(
                            this,
                            LinearLayout.VERTICAL
                        )
                    )
                }
            } else {
                Log.d("dddd", "satir3")

                adapter?.updateData(data.sources)
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