package com.samuelseptiano.mvvmroom.data.datasource.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.samuelseptiano.mvvmroom.data.model.roommodel.remotekeys.SearchRemoteKeys

/**
 * Created by samuel.septiano on 14/02/2026.
 */
@Dao
interface SearchRemoteKeysDao {

    @Query("SELECT * FROM search_remote_keys WHERE newsId = :id")
    suspend fun getSearchRemoteKeysByNewsId(id: String): SearchRemoteKeys?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSearchRemoteKeysAll(remoteKeys: List<SearchRemoteKeys>)

    @Query("DELETE FROM search_remote_keys")
    suspend fun clearSearchRemoteKeys()
}