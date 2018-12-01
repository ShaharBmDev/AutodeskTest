package il.co.sbm.autodesktest.model.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import il.co.sbm.autodesktest.model.network.NewsApiService
import il.co.sbm.autodesktest.model.network.objects.response.NewsApiResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * A repository to obtain and mange news articles from the NewsApi
 */
object NewsRepository {
    private val mNewsApiService: NewsApiService by lazy { NewsApiService.create() }
    private val mNewsApiResponse: MutableLiveData<Resource<NewsApiResponse>> by lazy { MutableLiveData<Resource<NewsApiResponse>>() }

    /**
     * Gets a reference of the live data object into which the data will be received.
     */
    fun getTopHeadlines(): LiveData<Resource<NewsApiResponse>> {

        return mNewsApiResponse
    }

    /**
     * Invokes a ApiCall to NewsApi to get the latest articles.
     */
    fun refreshTopHeadlines() {

        //propagates that the loading has started to whomever is observing this livedata object.
        mNewsApiResponse.value = Resource(Resource.Status.LOADING, null, null)

        //enqueues an api call to News api to get the top articles from israel.
        mNewsApiService.topHeadlinesIsrael().enqueue(object : Callback<NewsApiResponse> {

            override fun onFailure(call: Call<NewsApiResponse>, t: Throwable) {
                //propagates that the loading has finished with an error.
                mNewsApiResponse.value = Resource(Resource.Status.ERROR, null, t.message)
            }

            override fun onResponse(call: Call<NewsApiResponse>, response: Response<NewsApiResponse>) {
                //propagates that the loading has finished successfully.
                mNewsApiResponse.value = Resource(Resource.Status.SUCCESS, response.body(), null)
            }
        })
    }
}