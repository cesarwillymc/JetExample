package com.summit.jetexample.domain

import com.summit.jetexample.data.model.Poll
import com.summit.jetexample.vo.Result

interface Repo {
    suspend fun getAllPolls(): Result<List<Poll>>
    suspend fun createPoll(poll:Poll): Result<Boolean>
}