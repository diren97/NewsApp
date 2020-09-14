package com.diren.newsapp.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.diren.newsapp.model.News

@Database(entities = [News::class],version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun newsDao(): NewsDao

    companion object {
        private var newsRoomInstance: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase? {
            if (newsRoomInstance == null){
                synchronized(AppDatabase::class.java) {
                    if (newsRoomInstance == null){
                        newsRoomInstance = Room.databaseBuilder(context.applicationContext,
                            AppDatabase::class.java, "news_database").build()
                    }
                }
            }
            return newsRoomInstance
        }
    }

}