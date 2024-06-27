package com.onusant.apitask.presentation.login

import android.content.Context
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.onusant.apitask.data.repository.LoginErrorResponse
import com.onusant.apitask.data.repository.LoginRepository
import com.onusant.apitask.data.repository.PreferenceRepository
import com.onusant.apitask.data.service.LoginRequest
import com.onusant.apitask.datastore
import com.onusant.apitask.model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import io.ktor.util.Identity.encode
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: LoginRepository,
    @ApplicationContext private val context: Context
) : ViewModel() {

    private val _state = mutableStateOf(LoginScreenState())
    val state: State<LoginScreenState> = _state

    private val preferenceRepository = PreferenceRepository(context.datastore)

    private fun setUser(user: User) {
        viewModelScope.launch {
            preferenceRepository.setPreference(
                stringPreferencesKey("user"),
                Json.encodeToString(user)
            )
        }
    }

    fun onSubmit(identifier: String, password: String) {
        viewModelScope.launch {
            repository.login(
                payload = LoginRequest(identifier, password),
                onSuccess = { response ->
                    setUser(response)
                },
                onFailure = { response ->
                    _state.value = state.value.copy(
                        response = Json.decodeFromString<LoginErrorResponse>(response.message).error,
                    )
                },
                onStart = {
                    _state.value = state.value.copy(
                        loading = true,
                        response = "",
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