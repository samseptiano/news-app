package com.samuelseptiano.mvvmroom.data.repository.pager

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.samuelseptiano.mvvmroom.data.datasource.local.NewsDatabase
import com.samuelseptiano.mvvmroom.data.model.News
import com.samuelseptiano.mvvmroom.data.model.roommodel.HeadlineNewsRoomModel
import com.samuelseptiano.mvvmroom.data.repository.HeadlineRemoteMediator
import com.samuelseptiano.mvvmroom.data.repository.remote.NewsRepositoryImpl
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * Created by samuel.septiano on 14/02/2026.
 */
@OptIn(ExperimentalPagingApi::class)
class HeadlinePagerRepository @Inject constructor(
    private val database: NewsDatabase,
    private val newsRepository: NewsRepositoryImpl
) {

    fun getHeadlinePager(): Flow<PagingData<News>> {

        return Pager(
            config = PagingConfig(
                pageSize = 5,
                initialLoadSize = 5,
                prefetchDistance = 0,
                enablePlaceholders = true
            ),
            remoteMediator = HeadlineRemoteMediator(
                database = database,
                newsRepository = newsRepository
            ),
            pagingSourceFactory = {
                database.headlineNewsDao().getHeadlinePagingSource()
            }
        ).flow.map { pagingData ->
            pagingData.map { entity ->
                HeadlineNewsRoomModel.toNews(entity)
            }
        }
    }
}
