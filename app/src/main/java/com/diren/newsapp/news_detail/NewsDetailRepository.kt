package com.diren.newsapp.news_detail

import android.content.Context
import android.util.Log
import com.diren.newsapp.model.NewsResponse
import com.diren.newsapp.nonEmptyStringOrNull
import kotlinx.coroutines.flow.Flow

class NewsDetailRepository(context: Context) {
    private val dataSource: NewsDetailDataSource

    init {
        dataSource = NewsDetailDataSource(context)
    }

    suspend fun fetchNews(query: String?,source_id:String): Flow<NewsResponse> {
        if (query==null)
        Log.d("deneme", "query")
        return query?.nonEmptyStringOrNull()?.let { q ->
            dataSource.getNews()
        } ?: dataSource.getTopHeadlines(source_id)
            }
    }