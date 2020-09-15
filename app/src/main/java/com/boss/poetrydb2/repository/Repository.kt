package com.boss.poetrydb2.repository

import androidx.lifecycle.LiveData
import com.boss.poetrydb2.api.RetrofitBuilder
import com.boss.poetrydb2.model.AuthorList
import com.boss.poetrydb2.model.RandomPoem
import com.boss.poetrydb2.ui.state.ViewState
import com.boss.poetrydb2.util.ApiSuccessResponse
import com.boss.poetrydb2.util.DataState
import com.boss.poetrydb2.util.GenericApiResponse
import com.boss.poetrydb2.util.NetworkBoundResource

object Repository {
    fun getRandomPoem(): LiveData<DataState<ViewState>> {
        return object : NetworkBoundResource<RandomPoem, ViewState>() {
            override fun handleApiSuccessResponse(response: ApiSuccessResponse<RandomPoem>) {
                result.value = DataState.data(
                    data = ViewState(
                        randomPoem = response.body
                    )
                )
            }

            override fun createCall(): LiveData<GenericApiResponse<RandomPoem>> {
                return RetrofitBuilder.apiService.getRandomPoem()
            }
        }.asLiveData()
    }

    fun getAuthorList(): LiveData<DataState<ViewState>> {
        return object : NetworkBoundResource<AuthorList, ViewState>() {
            override fun handleApiSuccessResponse(response: ApiSuccessResponse<AuthorList>) {
                result.value = DataState.data(
                    data = ViewState(
                        authorList = response.body
                    )
                )
            }

            override fun createCall(): LiveData<GenericApiResponse<AuthorList>> {
                return RetrofitBuilder.apiService.getAuthorList()
            }
        }.asLiveData()
    }
}