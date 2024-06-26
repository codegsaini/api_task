package com.onusant.apitask.presentation.home

import com.onusant.apitask.model.Property

data class HomeScreenState(
    val recents: List<Property> = emptyList()
)