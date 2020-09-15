package com.boss.poetrydb2.ui

import com.boss.poetrydb2.util.DataState

interface DataStateListener {
    fun onDataStateChanged(dataState: DataState<*>?)
}