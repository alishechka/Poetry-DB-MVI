package com.boss.poetrydb2.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.boss.poetrydb2.model.AuthorList
import com.boss.poetrydb2.model.RandomPoem
import com.boss.poetrydb2.repository.Repository
import com.boss.poetrydb2.ui.state.StateEvent
import com.boss.poetrydb2.ui.state.StateEvent.*
import com.boss.poetrydb2.ui.state.ViewState
import com.boss.poetrydb2.util.DataState

class MainViewModel : ViewModel() {

    private val _stateEvent: MutableLiveData<StateEvent> = MutableLiveData()
    private val _viewState: MutableLiveData<ViewState> = MutableLiveData()

    val viewState: LiveData<ViewState>
        get() = _viewState

    val dataState: LiveData<DataState<ViewState>> = Transformations
        .switchMap(_stateEvent) {
            it?.let {
                handleStateEvent(it)
            }
        }

    private fun handleStateEvent(stateEvent: StateEvent): LiveData<DataState<ViewState>> {
        println("DEBUG: New StateEvent detected: $stateEvent")
        when (stateEvent) {
            is GetRandomPoemEvent -> {
                return Repository.getRandomPoem()
            }
            is GetAuthorListEvent -> {
                return Repository.getAuthorList()
            }
        }
    }

    fun setRandomPoemData(randomPoem: RandomPoem) {
        val update = getCurrentViewStateOrNew()
        update.randomPoem = randomPoem
        _viewState.value = update
    }

    fun setAuthorListData(authorList: AuthorList) {
        val update = getCurrentViewStateOrNew()
        update.authorList = authorList
        _viewState.value = update
    }

    fun getCurrentViewStateOrNew(): ViewState {
        val value = viewState.value?.let {
            it
        } ?: ViewState()
        return value
    }

    fun setStateEvent(event: StateEvent) {
        _stateEvent.value = event
    }
}