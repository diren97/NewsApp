package com.diren.newsapp.news_detail

import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.diren.newsapp.ResultWrapper
import com.diren.newsapp.db.AppDatabase
import com.diren.newsapp.db.NewsDao
import com.diren.newsapp.failure
import com.diren.newsapp.loading
import com.diren.newsapp.model.News
import com.diren.newsapp.model.NewsResponse
import com.diren.newsapp.success
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class NewsDetailVM(application: Application) : AndroidViewModel(application) {

    private val repo = NewsDetailRepository(application)
    val newsLiveData = MutableLiveData<ResultWrapper<NewsResponse, Throwable>>()
    private var dataSource: NewsDetailDataSource = NewsDetailDataSource(application)
    private var apiJob: Job? = null
    private var newsDao: NewsDao
    val allNews: LiveData<List<News>>

    init {
        val newsDb = AppDatabase.getDatabase(application)
        newsDao = newsDb!!.newsDao()
        allNews = newsDao.getAllNews()
    }

    fun insert(news: News) {
        GlobalScope.async {
            newsDao.insertAll(news)
        }
    }

    fun delete(id: Int) {
        GlobalScope.async {
            newsDao.deleteById(id)
        }
    }

    companion object {
        private class InsertAsyncTask(private val newsDao: NewsDao) :
            AsyncTask<News, Void, Void?>() {
            override fun doInBackground(vararg news: News): Void? {
                newsDao.insertAll(news[0])
                return null
            }
        }
    }


    fun fetchNewsData(query: String? = null,source_id:String?) {
        apiJob?.cancel()
        apiJob = viewModelScope.launch() {
            newsLiveData.loading()
            if (source_id != null) {
                repo.fetchNews(query,source_id)
            }
            if (source_id != null) {
                dataSource.getTopHeadlines(source_id)
                    .catch { error ->
                        newsLiveData.failure(error)
                    }
                    .collect {
                        newsLiveData.success(it)
                    }
            }
        }
    }
}