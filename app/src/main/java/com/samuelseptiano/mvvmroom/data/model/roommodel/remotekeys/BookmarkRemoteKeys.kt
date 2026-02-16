package com.samuelseptiano.mvvmroom.data.model.roommodel.remotekeys

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by samuel.septiano on 15/02/2026.
 */
@Entity(tableName = "bookmark_remote_keys")
data class BookmarkRemoteKeys(
    @PrimaryKey val newsId: String,
    val prevKey: Int?,
    val nextKey: Int?
)