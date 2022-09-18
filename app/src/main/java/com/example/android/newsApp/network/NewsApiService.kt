package com.example.android.newsApp.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private const val BASE_URL = "https://newsapi.org/v2/"

/**
 * Build the Moshi object for Retrofit
 */
private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

/**
 * Use the Retrofit builder to build a retrofit object using a Moshi converter with our Moshi
 * object.
 */
private val retrofit = Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .baseUrl(BASE_URL)
        .build()


/**
 * A public interface that exposes the [getArticles] method
 */
interface NewsApiService {
    /**
     * Our method to get top headlines. Returns a Retrofit Call
     */
    @GET("top-headlines")
    fun getArticles(
        @Query("country") country: String,
        @Query("category") category: String,
        @Query("q") query: String?,
        @Query("apiKey") api_key: String
    ): Call<NewsResponse>
}

/**
 * A public Api object that exposes the lazy-initialized Retrofit service
 */
object NewsApi {
    val retrofitService : NewsApiService by lazy { retrofit.create(NewsApiService::class.java) }
}
