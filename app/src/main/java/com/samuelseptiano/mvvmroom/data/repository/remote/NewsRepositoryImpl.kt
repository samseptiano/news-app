package com.samuelseptiano.mvvmroom.data.repository.remote

import com.samuelseptiano.mvvmroom.BuildConfig.API_KEY
import com.samuelseptiano.mvvmroom.data.datasource.remote.ApiService
import com.samuelseptiano.mvvmroom.data.model.NewsResponse
import javax.inject.Inject


class NewsRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
) : NewsRepository {


    override suspend fun getEverything(
        query: String,
        from: String,
        sortBy: String,
        pageSize: Int,
        page: Int
    ): NewsResponse {
        return apiService.getEverything(query, from, sortBy, API_KEY, pageSize, page)
    }

    override suspend fun getTopHeadline(
        country: String,
        pageSize: Int,
        page: Int
    ): NewsResponse {
        return apiService.getTopHeadline(country, API_KEY, pageSize, page)

    }

}