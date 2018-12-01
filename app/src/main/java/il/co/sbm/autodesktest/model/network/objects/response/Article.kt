package il.co.sbm.autodesktest.model.network.objects.response

import com.fasterxml.jackson.annotation.JsonProperty

data class Article(
    @JsonProperty("author")
    val author: String?,
    @JsonProperty("content")
    val content: String?,
    @JsonProperty("description")
    val description: String?,
    @JsonProperty("publishedAt")
    val publishedAt: String?,
    @JsonProperty("source")
    val source: Source?,
    @JsonProperty("title")
    val title: String?,
    @JsonProperty("url")
    val url: String?,
    @JsonProperty("urlToImage")
    val urlToImage: String?
)