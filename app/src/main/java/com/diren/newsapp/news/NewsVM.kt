package com.diren.newsapp.news

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.diren.newsapp.ResultWrapper
import com.diren.newsapp.failure
import com.diren.newsapp.news_detail.NewsDetailDataSource
import com.diren.newsapp.loading
import com.diren.newsapp.model.NewsResponse
import com.diren.newsapp.success
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class NewsVM(application: Application) : AndroidViewModel(application) {

    val newsLiveData = MutableLiveData<ResultWrapper<NewsResponse, Throwable>>()
    private var dataSource: NewsDetailDataSource = NewsDetailDataSource(application)
    private var apiJob: Job? = null

    fun fetchNewsData(query: String? = null) {
        apiJob?.cancel()
        apiJob = viewModelScope.launch() {
            newsLiveData.loading()
            dataSource.getNews()
                .catch { error ->
                    newsLiveData.failure(error)
                }
                .collect {

                    newsLiveData.success(it)
                }
        }
    }
}


