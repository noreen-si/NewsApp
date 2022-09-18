package com.example.android.newsApp.overview

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.android.newsApp.network.NewsApi
import com.example.android.newsApp.network.Article
import androidx.lifecycle.viewModelScope
import com.example.android.newsApp.network.NewsResponse
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

enum class NewsStatus { LOADING, ERROR, DONE }
/**
 * The [ViewModel] that is attached to the [OverviewFragment].
 */
class OverviewViewModel : ViewModel() {

    // Stores status of request
    private val _status = MutableLiveData<NewsStatus>()

    // Externalized live data
    val status: LiveData<NewsStatus>
        get() = _status

    private val _articles = MutableLiveData<MutableList<Article>>()

    val articles: LiveData<MutableList<Article>>
        get() = _articles

    // Used for navigation
    private val _navigateToArticle = MutableLiveData<Article>()

    // Externalized data
    val navigateToArticle: LiveData<Article>
        get() = _navigateToArticle

    init {
        getNews("us", "general", null)
    }

    /**
     *  Uses a Retrofit call to get news. Also changes status accordingly for proper
     *  loading images.
     */
     private fun getNews(country: String, category: String, query: String?) {
        viewModelScope.launch {
            _status.value = NewsStatus.LOADING
            try {
                val call: Call<NewsResponse> = NewsApi.retrofitService.getArticles(country, category, query, "3a33550a847442ae93c6bcb570adabc4")
                try {
                    call.enqueue(object : Callback<NewsResponse> {
                        override fun onResponse(
                            call: Call<NewsResponse>,
                            response: Response<NewsResponse>
                        ) {
                            _articles.value = response.body()!!.articles
                        }

                        override fun onFailure(call: Call<NewsResponse>, t: Throwable) {
                            Log.i("News", t.toString())
                        }

                    })
                } catch (e: Exception) {
                    e.printStackTrace()
                }
                _status.value = NewsStatus.DONE
            } catch (e: Exception) {
                e.printStackTrace()
                _status.value = NewsStatus.ERROR
                _articles.value = ArrayList()
            }
        }
    }

    /**
     * @param article The [Article] that was selected.
     */
    fun showArticle(article: Article) {
        _navigateToArticle.value = article
    }

    /**
     * After the navigation has taken place, set navigateToArticle to null
     */
    fun showArticleComplete() {
        _navigateToArticle.value = null
    }

    /**
     * Fetch news according to category.
     */
    fun updateCategory(category: String) {
        getNews("us", category, null)
    }

    /**
     *  Search news by query.
     */
    fun searchNews(query: String?) {
        getNews("us", "general", query)
    }
}
