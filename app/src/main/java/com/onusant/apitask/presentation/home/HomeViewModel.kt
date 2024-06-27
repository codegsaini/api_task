package com.onusant.apitask.presentation.home

import android.content.Context
import android.util.Log
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
import com.onusant.apitask.model.RecentProperty
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val propertyRepository: PropertyRepository,
) : ViewModel() {

    private val _state = mutableStateOf(HomeScreenState())
    val state: MutableState<HomeScreenState> = _state


    init {
        getRecentProperties()
    }

    private fun getRecentProperties() {
        propertyRepository.getRecentProperties().onEach {
            _state.value = state.value.copy(
                recents = it
            )
        }
            .launchIn(viewModelScope)
    }

    fun addToRecent(id: Int) {
        viewModelScope.launch {
            propertyRepository.addRecent(RecentProperty(propertyId = id))
        }
    }

}