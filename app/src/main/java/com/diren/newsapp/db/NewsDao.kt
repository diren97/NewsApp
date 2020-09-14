package com.diren.newsapp.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface NewsDao {

    @Query("SELECT * FROM reading_list")
    fun getAllNews(): List<ReadingList>

    @Insert
    fun insertAll(vararg r_list: ReadingList)
}