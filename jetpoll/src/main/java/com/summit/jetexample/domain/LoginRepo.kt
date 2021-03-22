package com.summit.jetexample.domain

import com.summit.jetexample.data.model.AuthCredentials
import com.summit.jetexample.vo.Result

interface LoginRepo {
    suspend fun login(credentials:AuthCredentials):Result<Boolean>
    suspend fun register(credentials:AuthCredentials):Result<Boolean>
}