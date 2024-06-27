package com.onusant.apitask.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.onusant.apitask.model.Property
import com.onusant.apitask.model.RecentProperty
import com.onusant.apitask.room.dao.PropertyDao
import com.onusant.apitask.room.dao.RecentPropertyDao

@TypeConverters(Converters::class)
@Database(entities = [Property::class, RecentProperty::class], version = 2)
abstract class Database: RoomDatabase() {
    abstract fun propertyDao(): PropertyDao
    abstract fun recentsDao(): RecentPropertyDao
}