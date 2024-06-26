package com.onusant.apitask.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.onusant.apitask.model.Property
import kotlinx.coroutines.flow.Flow

@Dao
interface PropertyDao {

    @Query("SELECT * FROM property")
    fun getAllProperties() : Flow<List<Property>>

    @Query("SELECT * FROM property WHERE id IN (:recents)")
    fun getRecentProperties(recents: List<Int>) : List<Property>

    @Query("SELECT * FROM property WHERE id = :id")
    suspend fun getProperty(id: String) : Property

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addProperties(properties: List<Property>)

    @Query("DELETE FROM property")
    suspend fun truncateTable()

}