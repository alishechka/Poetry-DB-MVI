package com.boss.poetrydb2.api

import androidx.lifecycle.LiveData
import com.boss.poetrydb2.model.AuthorList
import com.boss.poetrydb2.model.RandomPoem
import com.boss.poetrydb2.util.GenericApiResponse
import retrofit2.http.GET

interface ApiService {
    @GET("random")
    fun getRandomPoem(): LiveData<GenericApiResponse<RandomPoem>>

    @GET("author")
    fun getAuthorList(): LiveData<GenericApiResponse<AuthorList>>

}