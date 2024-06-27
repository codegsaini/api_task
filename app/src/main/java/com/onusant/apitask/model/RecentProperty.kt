package com.onusant.apitask.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class RecentProperty(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val propertyId: Int
)
