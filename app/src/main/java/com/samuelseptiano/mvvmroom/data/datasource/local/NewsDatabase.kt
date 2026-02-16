package com.samuelseptiano.mvvmroom.data.datasource.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.samuelseptiano.mvvmroom.data.datasource.local.dao.BookmarkNewsDao
import com.samuelseptiano.mvvmroom.data.datasource.local.dao.BookmarkRemoteKeysDao
import com.samuelseptiano.mvvmroom.data.datasource.local.dao.HeadlineNewsDao
import com.samuelseptiano.mvvmroom.data.datasource.local.dao.HeadlineRemoteKeysDao
import com.samuelseptiano.mvvmroom.data.datasource.local.dao.SearchNewsDao
import com.samuelseptiano.mvvmroom.data.datasource.local.dao.SearchRemoteKeysDao
import com.samuelseptiano.mvvmroom.data.model.roommodel.HeadlineNewsRoomModel
import com.samuelseptiano.mvvmroom.data.model.roommodel.remotekeys.BookmarkRemoteKeys
import com.samuelseptiano.mvvmroom.data.model.roommodel.remotekeys.HeadlineRemoteKeys
import com.samuelseptiano.mvvmroom.data.model.roommodel.remotekeys.SearchRemoteKeys

@Database(
    version = 1,
    entities = [HeadlineNewsRoomModel::class, HeadlineRemoteKeys::class, BookmarkRemoteKeys::class, SearchRemoteKeys::class],
    exportSchema = false
)
abstract class NewsDatabase : RoomDatabase() {
    abstract fun headlineNewsDao(): HeadlineNewsDao
    abstract fun headlineRemoteKeysDao(): HeadlineRemoteKeysDao
    abstract fun bookmarkNewsDao(): BookmarkNewsDao
    abstract fun bookmarkRemoteKeysDao(): BookmarkRemoteKeysDao
    abstract fun searchRemoteKeysDao(): SearchRemoteKeysDao
    abstract fun searchNewsDao(): SearchNewsDao

}
