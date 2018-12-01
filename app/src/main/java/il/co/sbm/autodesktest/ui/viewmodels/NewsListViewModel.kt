package il.co.sbm.autodesktest.ui.viewmodels

import androidx.lifecycle.*
import il.co.sbm.autodesktest.model.network.objects.response.NewsApiResponse
import il.co.sbm.autodesktest.model.repositories.NewsRepository
import il.co.sbm.autodesktest.model.repositories.Resource

class NewsListViewModel : ViewModel(), LifecycleObserver {

    val mNewsApiResponse: LiveData<Resource<NewsApiResponse>> = NewsRepository.getTopHeadlines()

    /**
     * This method acts upon OnResume event to whomever it is registered as an observer.
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onResume() {
        NewsRepository.refreshTopHeadlines()
    }
}
