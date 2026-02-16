package com.samuelseptiano.mvvmroom.data.repository.remote

import com.samuelseptiano.mvvmroom.data.model.NewsResponse

interface NewsRepository {
    suspend fun getEverything(
        query: String,
       from: String,
       sortBy: String,
       pageSize: Int,
        page: Int,
        ): NewsResponse

    suspend fun getTopHeadline(
        country: String,
        pageSize: Int,
        page: Int,
    ): NewsResponse

}