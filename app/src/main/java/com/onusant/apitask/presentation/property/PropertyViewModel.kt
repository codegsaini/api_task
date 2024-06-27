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
import com.onusant.apitask.model.RecentProperty
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PropertyViewModel @Inject constructor(
    private val repository: PropertyRepository,
) : ViewModel() {

    private val _state = mutableStateOf(PropertyScreenState())
    val state: State<PropertyScreenState> = _state

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

    fun addToRecentProperty(id: Int) {
        viewModelScope.launch {
            repository.addRecent(RecentProperty(propertyId = id))
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