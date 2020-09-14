package com.diren.newsapp.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "fav_news")
data class News(
    @ColumnInfo(name = "title")
    var title:String,
    @ColumnInfo(name = "subject")
    var subject:String,
    @ColumnInfo(name = "description")
    var description:String) {
    @PrimaryKey(autoGenerate = true)
    var newsId: Int = 0
}