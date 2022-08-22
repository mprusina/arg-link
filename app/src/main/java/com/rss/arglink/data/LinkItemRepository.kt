package com.rss.arglink.data

import com.rss.arglink.data.api.LinkItemService
import com.rss.arglink.data.model.LinkItem
import javax.inject.Inject

class LinkItemRepository @Inject constructor(private val linkItemService: LinkItemService) {
    suspend fun getLinkItems(search: String) : List<LinkItem> {
        return linkItemService.getLinkItems(search).resultList
    }
}