package il.co.sbm.autodesktest.model.network

import il.co.sbm.autodesktest.model.network.objects.response.NewsApiResponse
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory
import retrofit2.http.GET

interface NewsApiService {

    companion object Factory {
        private const val NEWS_API_BASE_URL = "https://newsapi.org/"
        private const val COUNTRY_ISRAEL: String = "il"
        private const val NEWS_API_KEY = "f055926460964e30bd0401a9d6aa8646"

        /**
         * Generates a retrofit NewsApiService with a jackson converter to invoke api calls to NewsApi.
         */
        fun create(): NewsApiService {
            return Retrofit.Builder().baseUrl(NEWS_API_BASE_URL).addConverterFactory(JacksonConverterFactory.create())
                .build().create(NewsApiService::class.java)
        }
    }

    @GET("/v2/top-headlines?country=$COUNTRY_ISRAEL&apiKey=$NEWS_API_KEY")
    fun topHeadlinesIsrael(): Call<NewsApiResponse>
}