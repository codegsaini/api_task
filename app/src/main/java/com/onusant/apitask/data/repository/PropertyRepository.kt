package com.onusant.apitask.data.repository

import com.onusant.apitask.data.service.PropertyService
import com.onusant.apitask.model.Property
import com.onusant.apitask.model.RecentProperty
import com.onusant.apitask.room.dao.PropertyDao
import com.onusant.apitask.room.dao.RecentPropertyDao
import com.onusant.apitask.util.Network.Companion.handleAPIResponse
import com.onusant.apitask.util.Response
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class PropertyRepository(
    private val propertyService: PropertyService,
    private val dao: PropertyDao,
    private val recentPropertyDao: RecentPropertyDao
) {

    suspend fun syncProperties(onSyncStart: () -> Unit, onSyncFinish: () -> Unit) {
        onSyncStart()
        val response = handleAPIResponse<List<Property>, Nothing> { propertyService.fetchProperties() }
        if (response is Response.Success) {
            withContext(Dispatchers.IO) {
                dao.truncateTable()
                dao.addProperties(response.data)
            }
        }
        onSyncFinish()
    }

    suspend fun getPropertyById(id: String) : Property {
        return dao.getProperty(id)
    }

    fun getProperties() : Flow<List<Property>> {
        return dao.getAllProperties()
    }

    fun getRecentProperties() : Flow<List<Property>> {
        return recentPropertyDao.getRecents()
    }

    suspend fun addRecent(recentProperty: RecentProperty) {
        recentPropertyDao.addRecent(recentProperty)
    }

}