package com.samuelseptiano.mvvmroom.data.datasource.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.samuelseptiano.mvvmroom.data.model.roommodel.remotekeys.BookmarkRemoteKeys

/**
 * Created by samuel.septiano on 14/02/2026.
 */
@Dao
interface BookmarkRemoteKeysDao {

    @Query("SELECT * FROM bookmark_remote_keys WHERE newsId = :id")
    suspend fun getBookmarkRemoteKeysByNewsId(id: String): BookmarkRemoteKeys?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBookmarkRemoteKeysAll(remoteKeys: List<BookmarkRemoteKeys>)

    @Query("DELETE FROM bookmark_remote_keys")
    suspend fun clearBookmarkRemoteKeys()
}