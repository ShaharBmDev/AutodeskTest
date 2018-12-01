package il.co.sbm.autodesktest

import il.co.sbm.autodesktest.model.network.NewsApiService
import il.co.sbm.autodesktest.model.network.objects.response.NewsApiResponse
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class WebApiTest {

    /**
     * Tests if headlines of NewsApi returns valid results.
     */
    @Test
    fun webApiResultSuccess() {
        val result = NewsApiService.create().topHeadlinesIsrael().execute()
        assert(result.isSuccessful && result.body()?.status == NewsApiResponse.STATUS_OK && result.body()?.articles != null)
    }
}