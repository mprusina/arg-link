package com.rss.arglink.data.api

import com.rss.arglink.data.model.LinkItemResultList
import retrofit2.http.GET
import retrofit2.http.Query

interface LinkItemService {
    @GET("search/link-items?limit=15")
    suspend fun getLinkItems(@Query("q", encoded=true) search: String): LinkItemResultList
}