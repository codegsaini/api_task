package com.onusant.apitask.presentation.login

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.onusant.apitask.data.repository.LoginRepository
import com.onusant.apitask.data.service.LoginRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: LoginRepository
) : ViewModel() {

    private val _state = mutableStateOf(LoginScreenState())
    val state: State<LoginScreenState> = _state

    fun onSubmit(identifier: String, password: String) {
        viewModelScope.launch {
            repository.login(
                payload = LoginRequest(identifier, password),
                onSuccess = { response ->
                    _state.value = state.value.copy(
                        user = response
                    )
                },
                onFailure = { response ->
                    _state.value = state.value.copy(
                        response = response.message,
                        user = null
                    )
                },
                onStart = {
                    _state.value = state.value.copy(
                        loading = true,
                        response = "",
                        user = null
                    )
                },
                onFinish = {
                    _state.value = state.value.copy(
                        loading = false
                    )
                }
            )
        }
    }
}