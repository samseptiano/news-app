package com.samuelseptiano.mvvmroom.data.repository.local

import android.util.Log
import com.samuelseptiano.mvvmroom.data.datasource.local.dao.HeadlineNewsDao
import com.samuelseptiano.mvvmroom.data.model.roommodel.HeadlineNewsRoomModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalNewsRepositoryImpl @Inject constructor(
    private val dao: HeadlineNewsDao,
) : LocalNewsRepository {


    override suspend fun insertHeadlineNews(headlineNewsRoomModel: HeadlineNewsRoomModel) {
        return dao.insertHeadlineNews(headlineNewsRoomModel)
    }

    override suspend fun updateHeadlineNews(headlineNewsRoomModel: HeadlineNewsRoomModel) {
            return dao.updateBookmarkHeadlineNews(headlineNewsRoomModel.id, headlineNewsRoomModel.isBookmarked)
    }


    override suspend fun insertHeadlineNewsBatch(headlineNewsRoomModelList: List<HeadlineNewsRoomModel>) {
        return dao.insertHeadlineNewsBatch(headlineNewsRoomModelList)
    }


    override suspend fun getHeadlineNewsById(id: String): HeadlineNewsRoomModel? {
        return dao.getHeadlineNewsById(id)
    }



    override suspend fun deleteHeadlineNewsById(id: String) {
        return dao.deleteHeadlineNewsById(id)
    }

    override suspend fun deleteHeadlineNewsAll() {
        return dao.deleteHeadlineNewsAll()
    }

    override fun searchHeadlines(query: String): Flow<List<HeadlineNewsRoomModel>> {
        return dao.searchHeadlines(query)
    }

    override suspend fun searchHeadlinesPaging(
        limit: Int,
        offset: Int
    ): List<HeadlineNewsRoomModel> {
        return dao.searchHeadlinesPaging( limit, offset)

    }
}