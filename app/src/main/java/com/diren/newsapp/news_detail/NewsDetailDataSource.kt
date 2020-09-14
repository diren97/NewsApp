package com.diren.newsapp.news_detail

import android.content.Context
import com.diren.newsapp.model.NewsResponse
import com.diren.newsapp.network.NetworkClient
import com.diren.newsapp.network.NetworkException
import com.diren.newsapp.nonEmptyStringOrNull
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.flow
import retrofit2.Response

class NewsDetailDataSource(context: Context) {
    private val API_KEY = "9f228f2555684152b2fe060578abf799"

    suspend fun getTopHeadlines(source_id:String): Flow<NewsResponse> {
        return flow {
            val response = NetworkClient.client.getTopHeadlines(API_KEY, source_id)

            successOrThrow(response, this)
        }
    }

    suspend fun getNews(): Flow<NewsResponse> {
        return flow {
            val response = NetworkClient.client.getNewsSource(
                API_KEY
            )
            successOrThrow(response, this)
        }
    }


    private suspend fun <T> successOrThrow(response: Response<T>, flowCollector: FlowCollector<T>) {
        if (response.isSuccessful) {
            response.body()?.let {
                flowCollector.emit(it)
                return
            }
        }

        val errorMessage =
            response.errorBody()?.string()?.nonEmptyStringOrNull()
                ?: "Something went wrong."

        throw NetworkException(
            response.code(),
            response.raw().request().url().toString(),
            errorMessage
        )
    }
}