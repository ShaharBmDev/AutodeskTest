package il.co.sbm.autodesktest.model.network.objects.response

import com.fasterxml.jackson.annotation.JsonProperty

data class NewsApiResponse(
    @JsonProperty("articles")
    val articles: List<Article>?,
    @JsonProperty("status")
    val status: String?,
    @JsonProperty("totalResults")
    val totalResults: Int,
    @JsonProperty("code")
    val code: String?,
    @JsonProperty("message")
    val message: String?
)
{
    companion object {
        const val STATUS_OK: String = "ok"
        const val STATUS_ERROR: String = "error"
    }
}
