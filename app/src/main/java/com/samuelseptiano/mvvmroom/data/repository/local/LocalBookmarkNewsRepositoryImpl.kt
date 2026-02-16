package com.samuelseptiano.mvvmroom.data.repository.local

import android.util.Log
import androidx.paging.PagingSource
import com.samuelseptiano.mvvmroom.data.datasource.local.dao.BookmarkNewsDao
import com.samuelseptiano.mvvmroom.data.datasource.local.dao.HeadlineNewsDao
import com.samuelseptiano.mvvmroom.data.model.roommodel.HeadlineNewsRoomModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalBookmarkNewsRepositoryImpl @Inject constructor(
    private val dao: BookmarkNewsDao,
) : LocalBookmarkNewsRepository {
    override suspend fun getBookmarkNewsById(id: String): HeadlineNewsRoomModel? {
        return dao.getBookmarkNewsById(id)
    }

    override fun searchBookmarks(query: String): Flow<List<HeadlineNewsRoomModel>> {
        return dao.searchBookmarks(query)
    }

    override suspend fun bookmarkCountBefore(newsId: String): Int {
        return dao.bookmarkCountBefore(newsId)
    }

    override suspend fun searchBookmarksPaging(
        limit: Int,
        offset: Int
    ): List<HeadlineNewsRoomModel> {
        return dao.searchBookmarksPaging(limit, offset)
    }

    override suspend fun updateBookmarkHeadlineNews(
       headlineNewsRoomModel: HeadlineNewsRoomModel
    ) {
        return dao.updateBookmarkHeadlineNews(headlineNewsRoomModel.id, headlineNewsRoomModel.isBookmarked)
    }

    override fun getBookmarksPagingSource(): PagingSource<Int, HeadlineNewsRoomModel> {
        return dao.getBookmarksPagingSource()
    }

    override suspend fun getBookmarksCount(): Int {
       return dao.getBookmarksCount()

    }


}