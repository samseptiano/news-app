package com.samuelseptiano.mvvmroom.data.model.roommodel

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.samuelseptiano.mvvmroom.data.model.News
import com.samuelseptiano.mvvmroom.data.model.Source

/**
 * Created by samuel.septiano on 24/10/2025.
 */
@Entity(tableName = "headline_news")
data class HeadlineNewsRoomModel(
    @PrimaryKey
    @SerializedName("id")
    val id: String,
    @SerializedName("sourceId")
    val sourceId: String?,
    @SerializedName("sourceName")
    val sourceName: String,
    @SerializedName("author")
    val author: String? = null,
    @SerializedName("title")
    val title: String,
    @SerializedName("description")
    val description: String? = null,
    @SerializedName("url")
    val url: String,
    @SerializedName("urlToImage")
    val urlToImage: String? = null,
    @SerializedName("publishedAt")
    val publishedAt: String,
    @SerializedName("content")
    val content: String? = null,
    @SerializedName("type")
    val type: String,
    @SerializedName("isBookMarked")
    val isBookmarked: Boolean,
) {
    companion object {
        fun fromNews(news: News, type: String): HeadlineNewsRoomModel {
            return HeadlineNewsRoomModel(
                id = news.id ?: news.url,
                sourceId = news.source.id,
                sourceName = news.source.name,
                author = news.author,
                title = news.title,
                description = news.description,
                url = news.url,
                urlToImage = news.urlToImage,
                publishedAt = news.publishedAt,
                content = news.content,
                type = type,
                isBookmarked = news.isBookmarked ?: false
            )
        }

        fun toNews(newsRoomModel: HeadlineNewsRoomModel): News {
            return News(
                id = newsRoomModel.id,
                source = Source(
                    id = newsRoomModel.sourceId,
                    name = newsRoomModel.sourceName
                ),
                author = newsRoomModel.author,
                title = newsRoomModel.title,
                description = newsRoomModel.description,
                url = newsRoomModel.url,
                urlToImage = newsRoomModel.urlToImage,
                publishedAt = newsRoomModel.publishedAt,
                content = newsRoomModel.content,
                type = newsRoomModel.type,
                isBookmarked = newsRoomModel.isBookmarked,

                )
        }

        fun dummy(): List<HeadlineNewsRoomModel> {
            return listOf(
                HeadlineNewsRoomModel(
                    id = "1",
                    sourceId = null,
                    sourceName = "Space.com",
                    author = "Tariq Malik",
                    title = "SpaceX Crew-12 mission latest news: It's docking day for Dragon astronauts - Space",
                    description = "Saturday, Feb. 14, 2026: Updates on SpaceX and NASA's Crew-12 astronaut mission to the International Space Station.",
                    url = "https://www.space.com/news/live/spacex-nasa-crew-12-astronauts-launch-to-iss-feb-14-2026",
                    urlToImage = "https://cdn.mos.cms.futurecdn.net/V49iRDPWCMcR4UaEYgLs2M-1920-80.png",
                    publishedAt = "2026-02-14T12:21:02Z",
                    content = "2026-02-14T12:21:03.073ZA Valentine's Day docking dawns...",
                    type = "headline",
                    isBookmarked = false
                ),
                HeadlineNewsRoomModel(
                    id = "2",
                    sourceId = null,
                    sourceName = "NPR",
                    author = "",
                    title = "ChatGPT promised to help her find her soulmate. Then it betrayed her - NPR",
                    description = "ChatGPT sent screenwriter Micky Small down a fantastical rabbit hole. Now, she's finding her way out.",
                    url = "https://www.npr.org/2026/02/14/nx-s1-5711441/ai-chatgpt-openai-love-betrayal-delusion-chatbot",
                    urlToImage = "https://npr.brightspotcdn.com/dims3/default/strip/false/crop/6720x3778+0+244/resize/1400/quality/85/format/jpeg/?url=http%3A%2F%2Fnpr-brightspot.s3.amazonaws.com%2F6d%2F68%2F7c8b69b941d8ab036302ee2ac840%2Fimg-8158.jpg",
                    publishedAt = "2026-02-14T10:00:00Z",
                    content = "Micky Small is one of hundreds of millions of people who regularly use AI chatbots...",
                    type = "headline",
                    isBookmarked = false
                ),
                HeadlineNewsRoomModel(
                    id = "3",
                    sourceId = "axios",
                    sourceName = "Axios",
                    author = "Sara Fischer",
                    title = "Scoop: Disney sends cease and desist letter to ByteDance over Seedance 2.0 - Axios",
                    description = "The letter includes a slew of examples of infringing Seedance videos that feature Disney's copyrighted characters.",
                    url = "https://www.axios.com/2026/02/13/disney-bytedance-seedance",
                    urlToImage = "https://images.axios.com/xZw6vlCbPZ8jjaCnSqO6e4Oe7pg=/0x0:1920x1080/1366x768/2025/12/11/1765467090649.jpeg",
                    publishedAt = "2026-02-14T09:44:05Z",
                    content = "Zoom in: The letter, addressed to ByteDance global general counsel John Rogovin...",
                    type = "headline",
                    isBookmarked = false
                ),
                HeadlineNewsRoomModel(
                    id = "4",
                    sourceId = "npr",
                    sourceName = "NPR",
                    author = "Micky Small",
                    title = "Headline 4: AI and soulmates",
                    description = "ChatGPT sent screenwriter down a fantastical rabbit hole.",
                    url = "https://www.npr.org/news4",
                    urlToImage = "https://npr.brightspotcdn.com/news4.jpg",
                    publishedAt = "2026-02-14T10:00:00Z",
                    content = "Micky Small is finding her way out of AI delusion...",
                    type = "headline",
                    isBookmarked = false
                ),
                HeadlineNewsRoomModel(
                    id = "5",
                    sourceId = "cnbc",
                    sourceName = "CNBC",
                    author = "Sam Meredith",
                    title = "Headline 5: Cuba energy crisis",
                    description = "Cuba’s government appears on the brink of economic collapse.",
                    url = "https://www.cnbc.com/news5",
                    urlToImage = "https://image.cnbcfm.com/news5.jpeg",
                    publishedAt = "2026-02-14T06:58:06Z",
                    content = "Cuba is running out of jet fuel...",
                    type = "headline",
                    isBookmarked = false
                ),
                HeadlineNewsRoomModel(
                    id = "6",
                    sourceId = "wp",
                    sourceName = "Washington Post",
                    author = "Dan Diamond",
                    title = "Headline 6",
                    description = "Desc 6",
                    url = "https://www.washingtonpost.com/news6",
                    urlToImage = "https://washingtonpost.com/news6.jpg",
                    publishedAt = "2026-02-14T04:26:00Z",
                    content = "Content 6...",
                    type = "headline",
                    isBookmarked = false
                ),
                HeadlineNewsRoomModel(
                    id = "7",
                    sourceId = "ap",
                    sourceName = "Associated Press",
                    author = "Jim Morris",
                    title = "Headline 7",
                    description = "Desc 7",
                    url = "https://apnews.com/news7",
                    urlToImage = "https://apnews.com/news7.jpg",
                    publishedAt = "2026-02-14T02:58:00Z",
                    content = "Content 7...",
                    type = "headline",
                    isBookmarked = false
                ),
                HeadlineNewsRoomModel(
                    id = "8",
                    sourceId = "gulf",
                    sourceName = "Gulf Coast News",
                    author = "Brian Wiechert",
                    title = "Headline 8",
                    description = "Desc 8",
                    url = "https://www.gulfcoastnewsnow.com/news8",
                    urlToImage = "https://gulfcoastnewsnow.com/news8.jpg",
                    publishedAt = "2026-02-14T02:37:00Z",
                    content = "Content 8...",
                    type = "headline",
                    isBookmarked = false
                ),
                HeadlineNewsRoomModel(
                    id = "9",
                    sourceId = "nypost",
                    sourceName = "New York Post",
                    author = "Mollie Walker",
                    title = "Headline 9",
                    description = "Desc 9",
                    url = "https://nypost.com/news9",
                    urlToImage = "https://nypost.com/news9.jpg",
                    publishedAt = "2026-02-14T02:12:00Z",
                    content = "Content 9...",
                    type = "headline",
                    isBookmarked = false
                ),
                HeadlineNewsRoomModel(
                    id = "10",
                    sourceId = "eleven",
                    sourceName = "Eleven Warriors",
                    author = null,
                    title = "Headline 10",
                    description = "Desc 10",
                    url = "https://www.elevenwarriors.com/news10",
                    urlToImage = "https://www.elevenwarriors.com/news10.jpg",
                    publishedAt = "2026-02-14T02:09:16Z",
                    content = "Content 10...",
                    type = "headline",
                    isBookmarked = false
                ),
                HeadlineNewsRoomModel(
                    id = "11",
                    sourceId = "ringside",
                    sourceName = "Ringside News",
                    author = "Steve Carrier",
                    title = "Headline 11",
                    description = "Desc 11",
                    url = "https://www.ringsidenews.com/news11",
                    urlToImage = "https://www.ringsidenews.com/news11.jpg",
                    publishedAt = "2026-02-14T00:17:11Z",
                    content = "Content 11...",
                    type = "headline",
                    isBookmarked = false
                ),
                HeadlineNewsRoomModel(
                    id = "12",
                    sourceId = "bbc",
                    sourceName = "BBC News",
                    author = null,
                    title = "Headline 12",
                    description = "Desc 12",
                    url = "https://www.bbc.com/news12",
                    urlToImage = "https://bbc.com/news12.jpg",
                    publishedAt = "2026-02-14T00:03:33Z",
                    content = "Content 12...",
                    type = "headline",
                    isBookmarked = false
                ),
                HeadlineNewsRoomModel(
                    id = "13",
                    sourceId = "ars",
                    sourceName = "Ars Technica",
                    author = "Beth Mole",
                    title = "Headline 13",
                    description = "Desc 13",
                    url = "https://arstechnica.com/news13",
                    urlToImage = "https://arstechnica.com/news13.jpg",
                    publishedAt = "2026-02-13T23:16:48Z",
                    content = "Content 13...",
                    type = "headline",
                    isBookmarked = false
                ),
                HeadlineNewsRoomModel(
                    id = "14",
                    sourceId = "espn",
                    sourceName = "ESPN",
                    author = "ESPN staff",
                    title = "Headline 14",
                    description = "Desc 14",
                    url = "https://www.espn.com/news14",
                    urlToImage = "https://www.espn.com/news14.jpg",
                    publishedAt = "2026-02-13T23:05:23Z",
                    content = "Content 14...",
                    type = "headline",
                    isBookmarked = false
                ),
                HeadlineNewsRoomModel(
                    id = "15",
                    sourceId = "bbc",
                    sourceName = "BBC News",
                    author = null,
                    title = "Headline 15",
                    description = "Desc 15",
                    url = "https://www.bbc.com/news15",
                    urlToImage = "https://bbc.com/news15.jpg",
                    publishedAt = "2026-02-13T22:12:03Z",
                    content = "Content 15...",
                    type = "headline",
                    isBookmarked = false
                ),
                HeadlineNewsRoomModel(
                    id = "16",
                    sourceId = "business",
                    sourceName = "Business Insider",
                    author = "Pranav Dixit",
                    title = "Headline 16",
                    description = "Desc 16",
                    url = "https://www.businessinsider.com/news16",
                    urlToImage = "https://www.businessinsider.com/news16.jpg",
                    publishedAt = "2026-02-13T19:20:00Z",
                    content = "Content 16...",
                    type = "headline",
                    isBookmarked = false
                ),
                HeadlineNewsRoomModel(
                    id = "17",
                    sourceId = "starmag",
                    sourceName = "Star Magazine",
                    author = "Olivia Bellusci",
                    title = "Headline 17",
                    description = "Desc 17",
                    url = "https://starmagazine.com/news17",
                    urlToImage = "https://starmagazine.com/news17.jpg",
                    publishedAt = "2026-02-13T17:04:22Z",
                    content = "Content 17...",
                    type = "headline",
                    isBookmarked = false
                ),
                HeadlineNewsRoomModel(
                    id = "18",
                    sourceId = "macrumors",
                    sourceName = "MacRumors",
                    author = "Hartley Charlton",
                    title = "Headline 18",
                    description = "Desc 18",
                    url = "https://www.macrumors.com/news18",
                    urlToImage = "https://www.macrumors.com/news18.jpg",
                    publishedAt = "2026-02-13T16:47:45Z",
                    content = "Content 18...",
                    type = "headline",
                    isBookmarked = false
                ),
                HeadlineNewsRoomModel(
                    id = "19",
                    sourceId = "cnn",
                    sourceName = "CNN",
                    author = "John Doe",
                    title = "Headline 19",
                    description = "Desc 19",
                    url = "https://www.cnn.com/news19",
                    urlToImage = "https://www.cnn.com/news19.jpg",
                    publishedAt = "2026-02-13T15:00:00Z",
                    content = "Content 19...",
                    type = "headline",
                    isBookmarked = false
                ),
                HeadlineNewsRoomModel(
                    id = "20",
                    sourceId = "nyt",
                    sourceName = "New York Times",
                    author = "Jane Doe",
                    title = "Headline 20",
                    description = "Desc 20",
                    url = "https://www.nytimes.com/news20",
                    urlToImage = "https://www.nytimes.com/news20.jpg",
                    publishedAt = "2026-02-13T14:30:00Z",
                    content = "Content 20...",
                    type = "headline",
                    isBookmarked = false
                ),
                HeadlineNewsRoomModel(
                    id = "21",
                    sourceId = "reuters",
                    sourceName = "Reuters",
                    author = "Mark Smith",
                    title = "Headline 21",
                    description = "Desc 21",
                    url = "https://www.reuters.com/news21",
                    urlToImage = "https://www.reuters.com/news21.jpg",
                    publishedAt = "2026-02-13T14:00:00Z",
                    content = "Content 21...",
                    type = "headline",
                    isBookmarked = false
                ),
                HeadlineNewsRoomModel(
                    id = "22",
                    sourceId = "bbc",
                    sourceName = "BBC News",
                    author = null,
                    title = "Headline 22",
                    description = "Desc 22",
                    url = "https://www.bbc.com/news22",
                    urlToImage = "https://bbc.com/news22.jpg",
                    publishedAt = "2026-02-13T13:30:00Z",
                    content = "Content 22...",
                    type = "headline",
                    isBookmarked = false
                ),
                HeadlineNewsRoomModel(
                    id = "23",
                    sourceId = "cnn",
                    sourceName = "CNN",
                    author = "John Doe",
                    title = "Headline 23",
                    description = "Desc 23",
                    url = "https://www.cnn.com/news23",
                    urlToImage = "https://www.cnn.com/news23.jpg",
                    publishedAt = "2026-02-13T13:00:00Z",
                    content = "Content 23...",
                    type = "headline",
                    isBookmarked = false
                ),
                HeadlineNewsRoomModel(
                    id = "24",
                    sourceId = "nyt",
                    sourceName = "New York Times",
                    author = "Jane Doe",
                    title = "Headline 24",
                    description = "Desc 24",
                    url = "https://www.nytimes.com/news24",
                    urlToImage = "https://www.nytimes.com/news24.jpg",
                    publishedAt = "2026-02-13T12:30:00Z",
                    content = "Content 24...",
                    type = "headline",
                    isBookmarked = false
                ),
                HeadlineNewsRoomModel(
                    id = "25",
                    sourceId = "reuters",
                    sourceName = "Reuters",
                    author = "Mark Smith",
                    title = "Headline 25",
                    description = "Desc 25",
                    url = "https://www.reuters.com/news25",
                    urlToImage = "https://www.reuters.com/news25.jpg",
                    publishedAt = "2026-02-13T12:00:00Z",
                    content = "Content 25...",
                    type = "headline",
                    isBookmarked = false
                )
                // Add more items here as needed
            )

        }
    }
}