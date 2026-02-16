package com.samuelseptiano.mvvmroom.data.repository.local

import com.samuelseptiano.mvvmroom.data.model.roommodel.HeadlineNewsRoomModel
import kotlinx.coroutines.flow.Flow

interface LocalNewsRepository {

    suspend fun insertHeadlineNews(headlineNewsRoomModel: HeadlineNewsRoomModel)

    suspend fun updateHeadlineNews(headlineNewsRoomModel: HeadlineNewsRoomModel)

    suspend fun insertHeadlineNewsBatch(headlineNewsRoomModelList: List<HeadlineNewsRoomModel>)


    suspend fun getHeadlineNewsById(id: String): HeadlineNewsRoomModel?


    suspend fun deleteHeadlineNewsById(id: String)

    suspend fun deleteHeadlineNewsAll()


    fun searchHeadlines(query: String): Flow<List<HeadlineNewsRoomModel>>

    suspend fun searchHeadlinesPaging(
        limit: Int,
        offset: Int
    ): List<HeadlineNewsRoomModel>




}