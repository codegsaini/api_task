package com.onusant.apitask.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Entity
@Serializable
data class Property(
    @PrimaryKey
    val id: Int,
    val agent_id: Int?,
    val address: String,
    val amenities: List<Amenity>,
    val area: String,
    val bedroom: Int,
    val city: String,
    val created_at: String,
    val description: String,
    val image_path: String,
    val location: String,
    val media_path: String,
    val pincode: String,
    val price: String,
    val property_categories: String,
    val property_name: String,
    val property_type: String,
    val size: String,
    val state: String,
    val status: String,
    val subtype_id: Int,
    val updated_at: String,
    val user_id: Int,
    val vehicle: String
)


@Serializable
data class Amenity(
    val amenity_id: Int,
    val amenity: String,
    val category: String
)