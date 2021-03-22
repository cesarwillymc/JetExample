package com.summit.jetexample.domain

import com.summit.jetexample.data.DataSource
import com.summit.jetexample.data.model.Poll
import com.summit.jetexample.vo.Result

class RepoImpl(private val dataSource: DataSource):Repo{
    override suspend fun getAllPolls(): Result<List<Poll>> = dataSource.dummyPollList
    override suspend fun createPoll(poll: Poll): Result<Boolean> = dataSource.createPoll(poll)
}