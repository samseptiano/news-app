package com.samuelseptiano.mvvmroom.data.repository.local

import com.samuelseptiano.mvvmroom.data.model.roommodel.HeadlineNewsRoomModel
import kotlinx.coroutines.flow.Flow

interface LocalSearchNewsRepository {

    suspend fun insertSearchNews(headlineNewsRoomModel: HeadlineNewsRoomModel)

    suspend fun updateSearchNews(headlineNewsRoomModel: HeadlineNewsRoomModel)

    suspend fun insertSearchNewsBatch(headlineNewsRoomModelList: List<HeadlineNewsRoomModel>)


    suspend fun getSearchNewsById(id: String): HeadlineNewsRoomModel?


    suspend fun deleteSearchNewsById(id: String)

    suspend fun deleteSearchNewsAll()


    fun searchSearchs(query: String): Flow<List<HeadlineNewsRoomModel>>

    suspend fun searchSearchsPaging(
        query:String,
        limit: Int,
        offset: Int
    ): List<HeadlineNewsRoomModel>


}