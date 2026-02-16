package com.samuelseptiano.mvvmroom.data.datasource.remote

import com.samuelseptiano.mvvmroom.data.model.NewsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("v2/everything")
    suspend fun getEverything(
        @Query("q") query: String,
        @Query("from") from: String,
        @Query("sortBy") sortBy: String,
        @Query("apiKey") apiKey: String,
        @Query("pageSize") pageSize: Int,
        @Query("page") page: Int,

        ): NewsResponse

    @GET("v2/top-headlines")
    suspend fun getTopHeadline(
        @Query("country") country: String,
        @Query("apiKey") apiKey: String,
        @Query("pageSize") pageSize: Int,
        @Query("page") page: Int,
    ): NewsResponse
}