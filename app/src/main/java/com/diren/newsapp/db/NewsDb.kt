package com.diren.newsapp.db

import androidx.room.Database
import androidx.room.RoomDatabase
@Database(entities = [ReadingList::class],version = 1)
abstract class NewsDb:RoomDatabase() {

    abstract fun newsDao(): NewsDao
}