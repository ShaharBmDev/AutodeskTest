package il.co.sbm.autodesktest.ui.adapters

import android.content.Context
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import il.co.sbm.autodesktest.R
import il.co.sbm.autodesktest.model.network.objects.response.Article
import il.co.sbm.autodesktest.model.network.objects.response.NewsApiResponse
import il.co.sbm.autodesktest.model.repositories.Resource

class NewsAdapter(
    private val mContext: Context?,
    private val mNewsApiResponse: LiveData<Resource<NewsApiResponse>>?,
    private val mOnArticleItemClickListener: (Article) -> Unit
) : RecyclerView.Adapter<NewsAdapter.ViewHolder>() {

    /**
     * Creates and returns view holder.
     */
    override fun onCreateViewHolder(iParent: ViewGroup, iViewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.cell_news_article, iParent, false))
    }

    /**
     * Return number of articles.
     */
    override fun getItemCount(): Int {
        return mNewsApiResponse?.value?.data?.articles?.size ?: 0
    }

    /**
     * Binds the article data to the view holder.
     */
    override fun onBindViewHolder(iViewHolder: ViewHolder, iPosition: Int) {
        iViewHolder.bind(mNewsApiResponse?.value?.data?.articles?.get(iPosition), mOnArticleItemClickListener)
    }

    /**
     * Custom view holder to show an article element.
     */
    class ViewHolder(iItemView: View) : RecyclerView.ViewHolder(iItemView) {
        private val mIvPhoto: ImageView = iItemView.findViewById(R.id.iv_cellNews_photo)
        private val mTvTitle: TextView = iItemView.findViewById(R.id.tv_cellNews_title)
        private val mTvDate: TextView = iItemView.findViewById(R.id.tv_cellNews_date)
        private val mTvSecondaryTitle: TextView = iItemView.findViewById(R.id.tv_cellNews_secondaryTitle)

        /**
         * Binds the article data to the view holder.
         */
        fun bind(
            iCurrentArticle: Article?,
            iOnArticleItemClickListener: (Article) -> Unit
        ) {

            if (TextUtils.isEmpty(iCurrentArticle?.urlToImage)) {
                mIvPhoto.setImageResource(R.drawable.article_default_background)
            } else {
                Picasso.get().load(iCurrentArticle?.urlToImage)
                    .placeholder(R.drawable.article_default_background)
                    .error(R.drawable.article_default_background).fit().centerCrop().into(mIvPhoto)
            }
            mTvTitle.text = iCurrentArticle?.title ?: ""
            mTvDate.text = iCurrentArticle?.publishedAt ?: ""
            mTvSecondaryTitle.text = iCurrentArticle?.description ?: ""

            //Addes an event of item click
            itemView.setOnClickListener {
                if (iCurrentArticle != null) {
                    iOnArticleItemClickListener(iCurrentArticle)
                }
            }
        }
    }
}
