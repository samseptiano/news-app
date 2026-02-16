package com.samuelseptiano.mvvmroom.utils.networkconnection

sealed class ConnectionState {
    data object Available : ConnectionState()
    data object Unavailable : ConnectionState()
}