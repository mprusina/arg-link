package com.rss.arglink.data.model

import com.google.gson.annotations.SerializedName

data class LinkItem(
    @SerializedName("id") var id: String? = null,
    @SerializedName("item_id") var itemId: String? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("kind") var kind: String? = null,
    @SerializedName("logo_url") var logoUrl: String? = null
)
