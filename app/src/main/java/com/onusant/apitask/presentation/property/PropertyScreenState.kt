package com.onusant.apitask.presentation.property

import com.onusant.apitask.model.Property

data class PropertyScreenState(
    val properties: List<Property> = emptyList(),
    val loading: Boolean = true
)
