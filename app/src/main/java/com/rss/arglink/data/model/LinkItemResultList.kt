package com.rss.arglink.data.model

import com.google.gson.annotations.SerializedName

data class LinkItemResultList(
    @SerializedName("results") var resultList: List<LinkItem>
)
