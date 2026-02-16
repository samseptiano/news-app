package com.samuelseptiano.mvvmroom.data.datasource.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import com.samuelseptiano.mvvmroom.data.model.roommodel.HeadlineNewsRoomModel
import kotlinx.coroutines.flow.Flow

@Dao
interface BookmarkNewsDao {

    @Query("Select * From headline_news Where id = :id AND isBookmarked = 1 ")
    suspend fun getBookmarkNewsById(id: String): HeadlineNewsRoomModel?

    @Query(
        """
    SELECT * FROM headline_news
    WHERE author LIKE '%' || :query || '%'
       OR title LIKE '%' || :query || '%'
       OR description LIKE '%' || :query || '%'
       AND isBookmarked = 1
    """
    )
    fun searchBookmarks(query: String): Flow<List<HeadlineNewsRoomModel>>

    @Query("SELECT COUNT(*) FROM headline_news WHERE id < :newsId and isBookmarked = 1 ")
    suspend fun bookmarkCountBefore(newsId: String): Int

    @Query(
        """
    SELECT * FROM headline_news WHERE isBookmarked = 1 
    ORDER BY publishedAt DESC
    LIMIT :limit OFFSET :offset
    """
    )
    suspend fun searchBookmarksPaging(
        limit: Int,
        offset: Int
    ): List<HeadlineNewsRoomModel>

    @Query("UPDATE headline_news SET isBookmarked = :isBookmarked WHERE id = :id")
    suspend fun updateBookmarkHeadlineNews(id: String, isBookmarked: Boolean)


    @Query("SELECT * FROM headline_news where isBookmarked = 1 ")
    fun getBookmarksPagingSource(): PagingSource<Int, HeadlineNewsRoomModel>

    @Query("SELECT COUNT(*) FROM headline_news where isBookmarked = 1 ")
    suspend fun getBookmarksCount(): Int
}