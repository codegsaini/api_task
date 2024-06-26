package com.onusant.apitask.presentation.property

import android.content.Context
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.onusant.apitask.data.repository.PreferenceRepository
import com.onusant.apitask.data.repository.PropertyRepository
import com.onusant.apitask.datastore
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PropertyViewModel @Inject constructor(
    private val repository: PropertyRepository,
    @ApplicationContext private val context: Context
) : ViewModel() {

    private val _state = mutableStateOf(PropertyScreenState())
    val state: State<PropertyScreenState> = _state

    val preferences = PreferenceRepository(context.datastore)

    init {
        syncProperties()
        fetchProperties()
    }


    fun syncProperties() {
        viewModelScope.launch {
            repository.syncProperties(
                onSyncStart = {
                    _state.value = state.value.copy(
                        loading = state.value.properties.isEmpty()
                    )
                },
                onSyncFinish = {
                    _state.value = state.value.copy(
                        loading = false
                    )
                }
            )
        }
    }

    fun addPropertyToRecents(id: Int) {
        viewModelScope.launch {
            preferences.setPreference(
                stringPreferencesKey("recents"),
                id.toString()
            )
        }
    }

    private fun fetchProperties() {
        repository.getProperties().onEach {
            _state.value = state.value.copy(
                properties = it
            )
        }
            .launchIn(viewModelScope)
    }
}