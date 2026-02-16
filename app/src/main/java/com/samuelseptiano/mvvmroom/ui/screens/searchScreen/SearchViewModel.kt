package com.samuelseptiano.mvvmroom.ui.screens.searchScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.samuelseptiano.mvvmroom.data.model.News
import com.samuelseptiano.mvvmroom.data.model.roommodel.HeadlineNewsRoomModel
import com.samuelseptiano.mvvmroom.data.repository.local.LocalSearchNewsRepository
import com.samuelseptiano.mvvmroom.data.repository.pager.SearchPagerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchPagerRepository: SearchPagerRepository,
    private val localSearchNewsRepository: LocalSearchNewsRepository
) : ViewModel() {

    private val _query = MutableStateFlow("")
    @OptIn(ExperimentalCoroutinesApi::class)
    val searchs: Flow<PagingData<News>> =
        _query
            .debounce(500)
            .distinctUntilChanged()
            .flatMapLatest { query ->
                if (query.isBlank()) {
                    flowOf(PagingData.empty())
                } else {
                    searchPagerRepository.getSearchPager(query)
                }
            }
            .cachedIn(viewModelScope)

    fun search(query: String) {
        _query.value = query
    }

    fun setBookmark(news: News, type: String) {
        viewModelScope.launch {
            localSearchNewsRepository.updateSearchNews(
                HeadlineNewsRoomModel.fromNews(news, type)
            )
        }
    }

    fun refreshHeadlines() {
        _query.value = ""
    }
}