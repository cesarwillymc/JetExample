package com.summit.jetexample.domain

import com.summit.jetexample.data.LoginDataSource
import com.summit.jetexample.data.model.AuthCredentials
import com.summit.jetexample.vo.Result

class LoginRepoImpl(private val dataSource: LoginDataSource):LoginRepo {
    override suspend fun login(credentials:AuthCredentials): Result<Boolean> = dataSource.login(credentials)
    override suspend fun register(credentials: AuthCredentials): Result<Boolean> = dataSource.register(credentials)
}