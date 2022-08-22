package com.rss.arglink.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rss.arglink.data.LinkItemRepository
import com.rss.arglink.data.model.LinkItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LinkItemListViewModel @Inject constructor(private val repository: LinkItemRepository) : ViewModel() {

    private val _viewState = MutableStateFlow<LinkItemListViewState>(LinkItemListViewState.NoData)
    val viewState: StateFlow<LinkItemListViewState> = _viewState

    fun searchData(search: String) {
        _viewState.value = LinkItemListViewState.Loading

        viewModelScope.launch {
            runCatching {
                repository.getLinkItems(search)
            }.onFailure {
                _viewState.value = LinkItemListViewState.Error
            }.onSuccess {
                if (it.isEmpty()) {
                    _viewState.value = LinkItemListViewState.NoData
                } else {
                    _viewState.value = LinkItemListViewState.Success(it)
                }
            }
        }
    }
}

sealed class LinkItemListViewState {
    object Loading : LinkItemListViewState()
    object Error : LinkItemListViewState()
    object NoData : LinkItemListViewState()
    data class Success(val linkItemList: List<LinkItem>) : LinkItemListViewState()
}