package com.samuelseptiano.mvvmroom.data.datasource.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.samuelseptiano.mvvmroom.data.model.roommodel.HeadlineNewsRoomModel
import kotlinx.coroutines.flow.Flow

@Dao
interface HeadlineNewsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHeadlineNews(headlineNewsRoomModel: HeadlineNewsRoomModel)

    @Query("UPDATE headline_news SET isBookmarked = :isBookmarked WHERE id = :id")
    suspend fun updateBookmarkHeadlineNews(id: String, isBookmarked: Boolean)


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHeadlineNewsBatch(headlineNewsRoomModelList: List<HeadlineNewsRoomModel>)

    @Query("Select * From headline_news Where id = :id")
    suspend fun getHeadlineNewsById(id: String): HeadlineNewsRoomModel?


    @Query("DELETE FROM headline_news WHERE id = :id")
    suspend fun deleteHeadlineNewsById(id: String)

    @Query("DELETE FROM headline_news where type = 'headline' ")
    suspend fun deleteHeadlineNewsAll()

    @Query(
        """
    SELECT * FROM headline_news
    WHERE author LIKE '%' || :query || '%'
       OR title LIKE '%' || :query || '%'
       OR description LIKE '%' || :query || '%'
       AND type = 'headline'
    """
    )
    fun searchHeadlines(query: String): Flow<List<HeadlineNewsRoomModel>>

    @Query("SELECT COUNT(*) FROM headline_news WHERE id < :newsId and type='headline' ")
    suspend fun headlineCountBefore(newsId: String): Int

    @Query(
        """
    SELECT * FROM headline_news
        where type = 'headline'
    ORDER BY publishedAt DESC
    LIMIT :limit OFFSET :offset
    """
    )
    suspend fun searchHeadlinesPaging(
        limit: Int,
        offset: Int
    ): List<HeadlineNewsRoomModel>


    @Query("SELECT * FROM headline_news where type = 'headline' ")
    fun getHeadlinePagingSource(): PagingSource<Int, HeadlineNewsRoomModel>

    @Query("SELECT COUNT(*) FROM headline_news where type = 'headline' ")
    suspend fun getHeadlineCount(): Int
}