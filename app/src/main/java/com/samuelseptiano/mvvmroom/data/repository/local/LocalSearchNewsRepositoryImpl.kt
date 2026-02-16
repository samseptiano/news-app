package com.samuelseptiano.mvvmroom.data.repository.local

import com.samuelseptiano.mvvmroom.data.datasource.local.dao.SearchNewsDao
import com.samuelseptiano.mvvmroom.data.model.roommodel.HeadlineNewsRoomModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalSearchNewsRepositoryImpl @Inject constructor(
    private val dao: SearchNewsDao,
) : LocalSearchNewsRepository {
    override suspend fun insertSearchNews(headlineNewsRoomModel: HeadlineNewsRoomModel) {
        return dao.insertSearchNews(headlineNewsRoomModel)
    }

    override suspend fun updateSearchNews(headlineNewsRoomModel: HeadlineNewsRoomModel) {
        return dao.updateBookmarkSearchNews(
            headlineNewsRoomModel.id,
            headlineNewsRoomModel.isBookmarked!!
        )
    }

    override suspend fun insertSearchNewsBatch(headlineNewsRoomModelList: List<HeadlineNewsRoomModel>) {
        return dao.insertSearchNewsBatch(headlineNewsRoomModelList)
    }

    override suspend fun getSearchNewsById(id: String): HeadlineNewsRoomModel? {
        return dao.getSearchNewsById(id)
    }

    override suspend fun deleteSearchNewsById(id: String) {
        return dao.deleteSearchNewsById(id)
    }

    override suspend fun deleteSearchNewsAll() {
        return dao.deleteSearchNewsAll()
    }

    override fun searchSearchs(query: String): Flow<List<HeadlineNewsRoomModel>> {
        return dao.searchSearchs(query)
    }

    override suspend fun searchSearchsPaging(
        query: String,
        limit: Int,
        offset: Int
    ): List<HeadlineNewsRoomModel> {
        return dao.searchSearchPaging(query, limit, offset)

    }


}