package com.onusant.apitask.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Entity
@Serializable
data class User(
    @PrimaryKey
    val id: Int,
    val access_token: String,
    val username: String,
    val role: String,
    val phone_number: String,
    val email: String,
    val created_at: String,
    val expires_at: String,
)
