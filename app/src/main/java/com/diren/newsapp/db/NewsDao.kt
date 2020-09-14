package com.diren.newsapp.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.diren.newsapp.model.News
@Dao
interface NewsDao {

    @Query("SELECT * FROM fav_news")
    fun getAllNews(): LiveData<List<News>>

    @Insert
    fun insertAll(vararg note: News)

    @Query("DELETE from FAV_NEWS where newsId = :newsId")
    fun deleteById(vararg newsId : Int): Int


}