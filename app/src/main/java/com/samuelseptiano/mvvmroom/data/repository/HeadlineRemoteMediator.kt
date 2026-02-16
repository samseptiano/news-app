package com.samuelseptiano.mvvmroom.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.samuelseptiano.mvvmroom.data.datasource.local.NewsDatabase
import com.samuelseptiano.mvvmroom.data.model.roommodel.HeadlineNewsRoomModel
import com.samuelseptiano.mvvmroom.data.repository.remote.NewsRepositoryImpl

/**
 * Created by samuel.septiano on 14/02/2026.
 */
@OptIn(ExperimentalPagingApi::class)
class HeadlineRemoteMediator(
    private val database: NewsDatabase,
    private val newsRepository: NewsRepositoryImpl
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

        // ✅ Gunakan jumlah item yang sudah dimuat Paging
        val offset = when (loadType) {
            LoadType.REFRESH -> 0
            LoadType.PREPEND -> return MediatorResult.Success(true)
            LoadType.APPEND -> state.pages.sumOf { it.data.size }
        }

        // 1️⃣ Cek local DB dulu (top-N berdasarkan offset)
        val localData = database.headlineNewsDao()
            .searchHeadlinesPaging(
                limit = pageSize,
                offset = offset
            )

        if (localData.isNotEmpty()) {
            // Masih ada data di local → tidak perlu API
            return MediatorResult.Success(endOfPaginationReached = false)
        }

        // 2️⃣ Local habis → hit API berdasarkan offset
        val page = (offset / pageSize) + 1

        return try {

            val response = newsRepository.getTopHeadline(
                country = "us",
                pageSize = pageSize,
                page = page
            )

            val articles = response.articles
            val endOfPaginationReached = articles.isEmpty()

            database.withTransaction {

                if (loadType == LoadType.REFRESH) {
                    database.headlineNewsDao().deleteHeadlineNewsAll()
                }

                database.headlineNewsDao().insertHeadlineNewsBatch(
                    articles.map {
                        HeadlineNewsRoomModel.fromNews(it, "headline")
                    }
                )
            }

            MediatorResult.Success(
                endOfPaginationReached = endOfPaginationReached
            )

        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }

}


