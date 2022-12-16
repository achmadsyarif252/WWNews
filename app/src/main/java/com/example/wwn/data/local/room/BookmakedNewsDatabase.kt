package com.example.wnews.data.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.wnews.data.local.entity.BookmarkedNews
import com.example.wwn.data.local.room.BookmarkedNewsDao

@Database(entities = [BookmarkedNews::class], version = 1)
abstract class BookmakedNewsDatabase : RoomDatabase() {
    abstract fun bookmarkedDao(): BookmarkedNewsDao

    companion object {
        @Volatile
        private var INSTANCE: BookmakedNewsDatabase? = null

        @JvmStatic
        fun getDatabase(context: Context): BookmakedNewsDatabase {
            if (INSTANCE == null) {
                synchronized(BookmakedNewsDatabase::class.java) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        BookmakedNewsDatabase::class.java,
                        "fav_database"
                    ).build()
                }
            }
            return INSTANCE as BookmakedNewsDatabase
        }
    }

}