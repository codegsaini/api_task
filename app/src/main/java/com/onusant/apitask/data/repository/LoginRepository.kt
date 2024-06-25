package com.onusant.apitask.data.repository

import com.onusant.apitask.data.service.LoginRequest
import com.onusant.apitask.data.service.LoginService
import com.onusant.apitask.model.Property
import com.onusant.apitask.model.User
import com.onusant.apitask.util.Error
import com.onusant.apitask.util.Network.Companion.handleAPIResponse
import com.onusant.apitask.util.Response

class LoginRepository(private val loginService: LoginService) {

    suspend fun login(
        payload: LoginRequest,
        onSuccess: (Response<User>) -> Unit,
        onFailure: (Error) -> Unit,
        onStart: () -> Unit,
        onFinish: () -> Unit
    ) {
        onStart()
        val response = handleAPIResponse<User> { loginService.login(payload) }
        when (response) {
            is Response.Success -> {
                onSuccess(response)
            }
            is Response.Failure -> {
                onFailure(response.error)
            }
        }
        onFinish()
    }

}