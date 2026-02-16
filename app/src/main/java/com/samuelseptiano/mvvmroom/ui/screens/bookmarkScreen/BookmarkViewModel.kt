package com.samuelseptiano.mvvmroom.ui.screens.bookmarkScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.samuelseptiano.mvvmroom.data.model.News
import com.samuelseptiano.mvvmroom.data.model.roommodel.HeadlineNewsRoomModel
import com.samuelseptiano.mvvmroom.data.repository.local.LocalBookmarkNewsRepository
import com.samuelseptiano.mvvmroom.data.repository.local.LocalNewsRepository
import com.samuelseptiano.mvvmroom.data.repository.pager.BookmarkPagerRepository
import com.samuelseptiano.mvvmroom.data.repository.pager.HeadlinePagerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookmarkViewModel @Inject constructor(
    private val bookmarkPagerRepository: BookmarkPagerRepository,
    private val localBookmarkNewsRepository: LocalBookmarkNewsRepository
) : ViewModel() {

    private val _refreshTrigger = MutableStateFlow(Unit)
    @OptIn(ExperimentalCoroutinesApi::class)
    val bookmarks: Flow<PagingData<News>> = _refreshTrigger.flatMapLatest {
        bookmarkPagerRepository
            .getBookmarkPager()
            .cachedIn(viewModelScope)
    }
    fun setBookmark(news: News, type: String) {
        viewModelScope.launch {
            localBookmarkNewsRepository.updateBookmarkHeadlineNews(
                HeadlineNewsRoomModel.fromNews(news, type)
            )
        }
    }

    fun refreshHeadlines() {
        _refreshTrigger.value = Unit
    }
}