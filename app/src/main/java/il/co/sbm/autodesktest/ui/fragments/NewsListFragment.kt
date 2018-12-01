package il.co.sbm.autodesktest.ui.fragments

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import il.co.sbm.autodesktest.R
import il.co.sbm.autodesktest.model.network.objects.response.Article
import il.co.sbm.autodesktest.model.network.objects.response.NewsApiResponse
import il.co.sbm.autodesktest.model.repositories.Resource
import il.co.sbm.autodesktest.ui.adapters.NewsAdapter
import il.co.sbm.autodesktest.ui.viewmodels.NewsListViewModel
import il.co.sbm.autodesktest.utils.AppUtils
import il.co.sbm.autodesktest.utils.testUtils.EspressoTestingIdlingResource
import kotlinx.android.synthetic.main.fragment_news_list.*


class NewsListFragment : Fragment() {

    companion object {
        const val TAG: String = "NewsListFragment"
    }

    private lateinit var mViewModel: NewsListViewModel
    private var mNewsAdapter: NewsAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        EspressoTestingIdlingResource.increment()
        return inflater.inflate(R.layout.fragment_news_list, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        observeViewModel()
        observeLifeCycle()
    }

    /**
     * Starts observing the corresponding view model
     */
    private fun observeViewModel() {
        mViewModel = ViewModelProviders.of(this).get(NewsListViewModel::class.java)
        mViewModel.mNewsApiResponse.observe(this, Observer { iResponse ->
            handleNewsApiResponse(iResponse)
        })
    }

    /**
     * The viewModel starts observing this fragment lifecycle so that it will refresh the list upon resume
     *
     * This effectively refreshes the list when the app is started, when the fragment is resumed from background
     * and when resuming to this fragment from a different fragment.
     */
    private fun observeLifeCycle() {
        lifecycle.addObserver(mViewModel)
    }

    /**
     * Handles the response gathered from the NewsApi, with the following options to handle:
     *
     * [LOADING](Resource.Status.LOADING), [ERROR](Resource.Status.ERROR), [SUCCESS](Resource.Status.SUCCESS)
     *
     */
    private fun handleNewsApiResponse(iResponse: Resource<NewsApiResponse>?) {
        if (iResponse != null) {
            when (iResponse.status) {
                Resource.Status.LOADING -> {
                    //starts loading, showing progress bar
                    showProgressBar(true)
                }

                Resource.Status.ERROR -> {
                    //error occurred, showing error message and removing loader.
                    //not reloading list.
                    Toast.makeText(activity, getString(R.string.error_refreshing_feed), Toast.LENGTH_LONG).show()
                    showProgressBar(false)
                }

                Resource.Status.SUCCESS -> {
                    if (!TextUtils.isEmpty(iResponse.data?.status) && iResponse.data?.status.equals(NewsApiResponse.STATUS_OK)) {
                        //data received successfully loading to adapter
                        setAdapter()
                        EspressoTestingIdlingResource.decrement()
                    }
                    else
                    {
                        //even though api call succeeded, there was an error with the api itself. showing error message.
                        //not reloading list.
                        Toast.makeText(activity, getString(R.string.error_refreshing_feed), Toast.LENGTH_LONG).show()
                    }
                    //removing progress bar
                    showProgressBar(false)
                }
            }
        }
    }

    /**
     * Sets the recyclerview showing the articles adapter, or refreshes it if needed
     */
    private fun setAdapter() {
        if (rv_newsList_list.adapter == null) {
            initAdapter()
        } else {
            rv_newsList_list.adapter!!.notifyDataSetChanged()
        }
    }

    /**
     * Initiates the recyclerview's adapter showing the articles
     */
    private fun initAdapter() {
        if (mNewsAdapter == null) {
            mNewsAdapter = NewsAdapter(this.context!!, mViewModel.mNewsApiResponse)
            { clickedArticle: Article -> onArticleItemClick(clickedArticle) }
        }
        rv_newsList_list.layoutManager = LinearLayoutManager(this.context, RecyclerView.VERTICAL, false)
        rv_newsList_list.itemAnimator = DefaultItemAnimator()
        rv_newsList_list.adapter = mNewsAdapter
    }

    /**
     * Shows or hides the progress bar to indicate data loading from NewsApi
     */
    private fun showProgressBar(iShouldShow: Boolean) {
        pb_newsList_loading.visibility = if (iShouldShow) View.VISIBLE else View.GONE
    }

    /**
     * Event on article itemClick
     */
    private fun onArticleItemClick(iClickedArticle: Article) {
        openArticleWebViewFragment(iClickedArticle)
    }

    /**
     * Opens the article url in a separate, webview fragment, providing a url.
     */
    private fun openArticleWebViewFragment(iClickedArticle: Article) {
        val actionToWebViewFragment = NewsListFragmentDirections.ActionNewsListFragmentToWebViewFragment()
        actionToWebViewFragment.setUrl(AppUtils.getStringOrEmpty(iClickedArticle.url))
        NavHostFragment.findNavController(this).navigate(actionToWebViewFragment)
    }
}
