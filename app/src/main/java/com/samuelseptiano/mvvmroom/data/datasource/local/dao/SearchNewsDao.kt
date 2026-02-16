package com.samuelseptiano.mvvmroom.data.datasource.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.samuelseptiano.mvvmroom.data.model.roommodel.HeadlineNewsRoomModel
import kotlinx.coroutines.flow.Flow

@Dao
interface SearchNewsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSearchNews(headlineNewsRoomModel: HeadlineNewsRoomModel)

    @Query("UPDATE headline_news SET isBookmarked = :isBookmarked WHERE id = :id")
    suspend fun updateBookmarkSearchNews(id: String, isBookmarked: Boolean)


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSearchNewsBatch(headlineNewsRoomModelList: List<HeadlineNewsRoomModel>)

    @Query("Select * From headline_news Where id = :id")
    suspend fun getSearchNewsById(id: String): HeadlineNewsRoomModel?


    @Query("DELETE FROM headline_news WHERE id = :id")
    suspend fun deleteSearchNewsById(id: String)

    @Query("DELETE FROM headline_news where type = 'search' ")
    suspend fun deleteSearchNewsAll()

    @Query(
        """
    SELECT * FROM headline_news
    WHERE author LIKE '%' || :query || '%'
       OR title LIKE '%' || :query || '%'
       OR description LIKE '%' || :query || '%'
       AND type = 'search'
    """
    )
    fun searchSearchs(query: String): Flow<List<HeadlineNewsRoomModel>>

    @Query(
        """
    SELECT COUNT(*) FROM headline_news
        where type = 'search' AND id < :newsId
        AND author LIKE '%' || :query || '%'
       OR title LIKE '%' || :query || '%'
       OR description LIKE '%' || :query || '%'
    """
    )
    suspend fun searchCountBefore(newsId: String, query: String): Int

    @Query(
        """
    DELETE FROM headline_news
        where type = 'search'
        AND author LIKE '%' || :query || '%'
       OR title LIKE '%' || :query || '%'
       OR description LIKE '%' || :query || '%'
    """
    )
    suspend fun deleteByQuery(query: String)

    @Query(
        """
    SELECT * FROM headline_news
        where type = 'search'
        AND author LIKE '%' || :query || '%'
       OR title LIKE '%' || :query || '%'
       OR description LIKE '%' || :query || '%'
    LIMIT :limit OFFSET :offset
    """
    )
    suspend fun searchSearchPaging(
        query: String,
        limit: Int,
        offset: Int
    ): List<HeadlineNewsRoomModel>


    @Query(
        """
    SELECT * FROM headline_news
        where type = 'search'
        AND author LIKE '%' || :query || '%'
       OR title LIKE '%' || :query || '%'
       OR description LIKE '%' || :query || '%'
    """
    )
    fun getSearchPagingSource(query: String): PagingSource<Int, HeadlineNewsRoomModel>

    @Query("SELECT COUNT(*) FROM headline_news where type = 'search' ")
    suspend fun getSearchCount(): Int
}