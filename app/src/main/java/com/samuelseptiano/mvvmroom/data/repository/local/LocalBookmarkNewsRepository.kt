package com.samuelseptiano.mvvmroom.data.repository.local

import androidx.paging.PagingSource
import androidx.room.Query
import com.samuelseptiano.mvvmroom.data.model.roommodel.HeadlineNewsRoomModel
import kotlinx.coroutines.flow.Flow

interface LocalBookmarkNewsRepository {

    suspend fun getBookmarkNewsById(id: String): HeadlineNewsRoomModel?
    fun searchBookmarks(query: String): Flow<List<HeadlineNewsRoomModel>>
    suspend fun bookmarkCountBefore(newsId: String): Int
    suspend fun searchBookmarksPaging(
        limit: Int,
        offset: Int
    ): List<HeadlineNewsRoomModel>

    suspend fun updateBookmarkHeadlineNews(headlineNewsRoomModel: HeadlineNewsRoomModel)

    fun getBookmarksPagingSource(): PagingSource<Int, HeadlineNewsRoomModel>
    suspend fun getBookmarksCount(): Int


}