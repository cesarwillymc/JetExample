package com.summit.jetexample.state.livedata.domain

import com.summit.jetexample.state.livedata.data.model.User
import com.summit.jetexample.utils.Result

interface Repo {
    fun getUserList(): Result<List<User>>
}