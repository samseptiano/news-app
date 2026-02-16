package com.samuelseptiano.mvvmroom.ui.screens.headlineScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.samuelseptiano.mvvmroom.data.model.News
import com.samuelseptiano.mvvmroom.data.model.roommodel.HeadlineNewsRoomModel
import com.samuelseptiano.mvvmroom.data.repository.local.LocalNewsRepository
import com.samuelseptiano.mvvmroom.data.repository.pager.HeadlinePagerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HeadlineViewModel @Inject constructor(
    private val headlinePagerRepository: HeadlinePagerRepository,
    private val localNewsRepository: LocalNewsRepository
) : ViewModel() {

    private val _refreshTrigger = MutableStateFlow(Unit)
    @OptIn(ExperimentalCoroutinesApi::class)
    val headlines: Flow<PagingData<News>> = _refreshTrigger.flatMapLatest {
        headlinePagerRepository
            .getHeadlinePager()
            .cachedIn(viewModelScope)
    }
    fun setBookmark(news: News, type: String) {
        viewModelScope.launch {
            localNewsRepository.updateHeadlineNews(
                HeadlineNewsRoomModel.fromNews(news, type)
            )
        }
    }

    fun refreshHeadlines() {
        _refreshTrigger.value = Unit
    }
}