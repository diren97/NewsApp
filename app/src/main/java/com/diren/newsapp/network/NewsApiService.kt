package com.diren.newsapp.network

import com.diren.newsapp.model.IpInfoResponse
import com.diren.newsapp.model.NewsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface NewsApiService {

    @GET
    suspend fun getIpInfo(@Url url: String): Response<IpInfoResponse>

    @GET("/v2/top-headlines")
    suspend fun getTopHeadlines(
        @Query("apiKey") apiKey: String,
        @Query("sources") sources: String
    ): Response<NewsResponse>

    @GET("/v2/sources")
    suspend fun getNewsSource(
        @Query("apiKey") apiKey: String
    ): Response<NewsResponse>
}
