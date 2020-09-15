package com.boss.poetrydb2.ui.state

import com.boss.poetrydb2.model.AuthorList
import com.boss.poetrydb2.model.RandomPoem

data class ViewState(
    var authorList: AuthorList? = null,
    var randomPoem: RandomPoem? = null
)