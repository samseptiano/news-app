package com.samuelseptiano.mvvmroom.ui.state


data class UiState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)