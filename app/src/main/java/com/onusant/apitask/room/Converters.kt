package com.onusant.apitask.room

import androidx.room.TypeConverter
import com.onusant.apitask.model.Amenity
import kotlinx.serialization.json.Json
import kotlinx.serialization.encodeToString

class Converters {

    @TypeConverter
    fun listToString(value: List<String>) : String {
        return value.joinToString(",")
    }

    @TypeConverter
    fun stringToList(value: String) : List<String> {
        return value.split(",").map { it.trim() }
    }

    @TypeConverter
    fun fromAmenities(amenities: List<Amenity>?): String? {
        if (amenities == null) {
            return null
        }
        val json: String = Json.encodeToString(amenities)
        return json
    }

    @TypeConverter
    fun toAminities(amenitiesString: String?): List<Amenity>? {
        if (amenitiesString == null) {
            return null
        }
        val amenities: List<Amenity> = Json.decodeFromString<List<Amenity>>(amenitiesString)
        return amenities
    }
}