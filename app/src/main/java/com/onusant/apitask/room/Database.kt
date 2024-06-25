package com.onusant.apitask.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.onusant.apitask.model.Property
import com.onusant.apitask.room.dao.PropertyDao

@TypeConverters(Converters::class)
@Database(entities = [Property::class], version = 1)
abstract class Database: RoomDatabase() {
    abstract fun propertyDao(): PropertyDao
}