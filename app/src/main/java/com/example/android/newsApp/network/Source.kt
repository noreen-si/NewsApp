package com.example.android.newsApp.network

import android.os.Parcelable
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class Source(
    val id: String?,
    val name: String
) : Parcelable