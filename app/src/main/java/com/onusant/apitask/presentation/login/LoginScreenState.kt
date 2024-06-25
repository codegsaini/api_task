package com.onusant.apitask.presentation.login

import com.onusant.apitask.model.Property
import com.onusant.apitask.model.User

data class LoginScreenState(
    val response: String = "",
    val user: User? = null,
    val loading: Boolean = false
)
