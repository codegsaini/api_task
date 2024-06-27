package com.onusant.apitask.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.onusant.apitask.model.Property
import com.onusant.apitask.model.RecentProperty
import kotlinx.coroutines.flow.Flow

@Dao
interface RecentPropertyDao {

    @Query("SELECT * FROM property WHERE id IN (SELECT propertyId FROM recentproperty) LIMIT 10")
    fun getRecents() : Flow<List<Property>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addRecent(recentProperty: RecentProperty)

    @Query("DELETE FROM recentproperty WHERE propertyId = :id")
    suspend fun removeRecent(id: String)

    @Query("DELETE FROM recentproperty")
    suspend fun truncateTable()

}