package com.onusant.apitask.presentation.home

import android.content.Context
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateMapOf
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
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val propertyRepository: PropertyRepository,
    @ApplicationContext private val context: Context
) : ViewModel() {

    private val _state = mutableStateOf(HomeScreenState())
    val state: MutableState<HomeScreenState> = _state

    private val preferences = PreferenceRepository(context.datastore)

    init {
        getRecents()
    }

    private fun getRecents() {
        preferences.getPreference(stringPreferencesKey("recents")).onEach {
            if (it == null) return@onEach
            val properties = propertyRepository.getRecentProperties(
                it.split(",").toList().map { id -> id.toInt() }
            )
            _state.value = state.value.copy(
                recents = properties
            )
        }
            .launchIn(viewModelScope)
    }

}