package il.co.sbm.autodesktest.ui.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.*
import androidx.fragment.app.Fragment
import il.co.sbm.autodesktest.R
import kotlinx.android.synthetic.main.fragment_web_view.*

class WebViewFragment : Fragment() {

    companion object {
        const val TAG: String = "WebViewFragment"
        private const val URL: String = "url"
        private const val LOADING_FAIL: Int = -1
        private const val LOADING_SUCCESS: Int = 100
    }

    private val mUrl: String? by lazy {
        arguments?.getString(URL)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_web_view, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initWebView()
        loadUrl()
    }

    /**
     * Initiates the webview with all of it's specifications to allow page loading correctly as well as handeling loading errors.
     */
    @SuppressLint("SetJavaScriptEnabled")
    private fun initWebView() {

        wv_webView_container.settings.javaScriptEnabled = true
        wv_webView_container.settings.domStorageEnabled = true
        wv_webView_container.webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                super.onProgressChanged(view, newProgress)

                setProgress(newProgress)
            }
        }
        wv_webView_container.webViewClient = object : WebViewClient() {

            override fun onReceivedError(view: WebView?, request: WebResourceRequest?, error: WebResourceError?) {
                super.onReceivedError(view, request, error)
                handleError(request)
            }

            override fun onReceivedHttpError(
                view: WebView?, request: WebResourceRequest?,
                errorResponse: WebResourceResponse?
            ) {
                super.onReceivedHttpError(view, request, errorResponse)
                handleError(request)
            }
        }
    }

    /**
     * Checks if there was a problem loading the original url and showing the appropriate message if necessary
     */
    private fun handleError(iWebResourceRequest: WebResourceRequest?) {

        if (mUrl == null || mUrl == iWebResourceRequest?.url.toString()) {
            setProgress(LOADING_FAIL)
            tv_weView_errorMessage.visibility = View.VISIBLE
        }
    }

    /**
     * Updates progressbar to indicate percentage of page load.
     */
    private fun setProgress(iNewProgress: Int) {
        if (pb_webView_loading != null) {
            pb_webView_loading.progress = iNewProgress
            pb_webView_loading.visibility =
                    if (iNewProgress == LOADING_FAIL || iNewProgress == LOADING_SUCCESS) View.GONE else View.VISIBLE
        }
    }

    /**
     * Loads received url from bundle.
     */
    private fun loadUrl() {
        if (TextUtils.isEmpty(mUrl)) {
            handleError(null)
        } else {
            wv_webView_container.loadUrl(mUrl)
        }
    }


    override fun onDestroyView() {
        //stop loading because view is being destroyed
        wv_webView_container.stopLoading()
        super.onDestroyView()
    }
}
