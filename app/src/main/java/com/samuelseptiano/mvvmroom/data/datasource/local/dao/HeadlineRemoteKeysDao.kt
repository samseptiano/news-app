package com.samuelseptiano.mvvmroom.data.datasource.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.samuelseptiano.mvvmroom.data.model.roommodel.remotekeys.HeadlineRemoteKeys

/**
 * Created by samuel.septiano on 14/02/2026.
 */
@Dao
interface HeadlineRemoteKeysDao {

    @Query("SELECT * FROM headline_remote_keys WHERE newsId = :id")
    suspend fun getHeadlineRemoteKeysByNewsId(id: String): HeadlineRemoteKeys?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHeadlineRemoteKeysAll(remoteKeys: List<HeadlineRemoteKeys>)

    @Query("DELETE FROM headline_remote_keys")
    suspend fun clearHeadlineRemoteKeys()
}