package com.samuelseptiano.mvvmroom.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Created by samuel.septiano on 14/02/2026.
 */
@Parcelize
@kotlinx.serialization.Serializable
data class NewsResponse(
    val status: String,
    val totalResults: Int,
    val articles: List<News>
): Parcelable

@Parcelize
@kotlinx.serialization.Serializable
data class News(
    val id: String? = null,
    val source: Source,
    val author: String? = null,
    val title: String,
    val description: String? = null,
    val url: String,
    val urlToImage: String? = null,
    val publishedAt: String,
    val content: String? = null,
    val type: String? = null,
    var isBookmarked: Boolean? = false,
) : Parcelable


@Parcelize
@kotlinx.serialization.Serializable
data class Source(
    val id: String? = null,
    val name: String
) : Parcelable
