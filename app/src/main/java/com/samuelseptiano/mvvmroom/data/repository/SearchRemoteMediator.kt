package com.samuelseptiano.mvvmroom.data.repository

import android.util.Log
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
class SearchRemoteMediator(
    private val query: String,
    private val database: NewsDatabase,
    private val newsRepository: NewsRepositoryImpl
) : RemoteMediator<Int, HeadlineNewsRoomModel>() {

    override suspend fun initialize(): InitializeAction {
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, HeadlineNewsRoomModel>
    ): MediatorResult {

        val pageSize = state.config.pageSize

        val offset = when (loadType) {
            LoadType.REFRESH -> 0
            LoadType.PREPEND -> return MediatorResult.Success(true)
            LoadType.APPEND -> state.pages.sumOf { it.data.size }
        }

        // 1️⃣ Check local first
        val localData = database.searchNewsDao()
            .searchSearchPaging(
                query = query,
                limit = pageSize,
                offset = offset
            )

        if (localData.isNotEmpty()) {
            return MediatorResult.Success(
                endOfPaginationReached = false
            )
        }

//        // 2️⃣ If local empty → fetch API
        val page = (offset / pageSize) + 1

        return try {
            Log.d("SearchRemoteMediator", "Fetching data for query $query page $page")

            val response = newsRepository.getEverything(
                query = query,
                from = "",
                sortBy = "publishedAt",
                pageSize = pageSize,
                page = page
            )

            val articles = response.articles
            val endOfPaginationReached = articles.isEmpty()

            database.withTransaction {

                if (loadType == LoadType.REFRESH) {
                    database.searchNewsDao().deleteByQuery(query)
                }

                database.searchNewsDao().insertSearchNewsBatch(
                    articles.map {
                        HeadlineNewsRoomModel.fromNews(it, query)
                    }
                )
            }

            MediatorResult.Success(endOfPaginationReached)

        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }
}



