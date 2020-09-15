package com.boss.poetrydb2.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class AuthorList(
    @Expose
    @SerializedName("authors")
    val authors: List<String>
){
    override fun toString(): String {
        return "AuthorList(authors=$authors)"
    }
}