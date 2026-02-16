package com.samuelseptiano.mvvmroom.data.model.roommodel.remotekeys

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by samuel.septiano on 14/02/2026.
 */
@Entity(tableName = "headline_remote_keys")
data class HeadlineRemoteKeys(
    @PrimaryKey val newsId: String,
    val prevKey: Int?,
    val nextKey: Int?
)