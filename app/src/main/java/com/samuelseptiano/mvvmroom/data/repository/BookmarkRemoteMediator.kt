package com.samuelseptiano.mvvmroom.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.samuelseptiano.mvvmroom.data.datasource.local.NewsDatabase
import com.samuelseptiano.mvvmroom.data.model.roommodel.HeadlineNewsRoomModel

/**
 * Created by samuel.septiano on 14/02/2026.
 */
@OptIn(ExperimentalPagingApi::class)
class BookmarkRemoteMediator(
    private val database: NewsDatabase,
) : RemoteMediator<Int, HeadlineNewsRoomModel>() {

    override suspend fun initialize(): InitializeAction {
        // Refresh pertama kali app dibuka
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, HeadlineNewsRoomModel>
    ): MediatorResult {
        val pageSize = state.config.pageSize

        // Hitung offset untuk top-N local DB
        val offset = when (loadType) {
            LoadType.REFRESH -> 0
            LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
            LoadType.APPEND -> {
                val lastItem = state.lastItemOrNull()
                lastItem?.let {
                    database.bookmarkNewsDao()
                        .bookmarkCountBefore(it.id) // jumlah item sebelum lastItem.id
                } ?: 0
            }
        }

        // 1️⃣ Cek local DB dulu
        val localData =
            database.bookmarkNewsDao().searchBookmarksPaging(limit = pageSize, offset = offset)
        if (localData.isNotEmpty()) {
            // Ada data di DB → cukup tampilkan, tidak perlu API
            return MediatorResult.Success(endOfPaginationReached = false)
        }

        // 2️⃣ Jika DB kosong → panggil API
        return try {
            val endOfPaginationReached = true

            MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }
}


