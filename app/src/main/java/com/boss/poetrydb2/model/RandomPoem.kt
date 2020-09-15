package com.boss.poetrydb2.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class RandomPoem(
    @Expose
    @SerializedName("title")
    val title: String,
    @Expose
    @SerializedName("author")
    val author: String,
    @Expose
    @SerializedName("lines")
    val lines: List<String>,
    @Expose
    @SerializedName("linecount")
    val linecount: Int
) {
    override fun toString(): String {
        return "RandomPoem(title='$title', author='$author', lines=$lines, linecount=$linecount)"
    }
}

