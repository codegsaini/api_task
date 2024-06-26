package com.onusant.apitask.data.repository

import com.onusant.apitask.data.service.PropertyService
import com.onusant.apitask.model.Property
import com.onusant.apitask.room.dao.PropertyDao
import com.onusant.apitask.util.Network.Companion.handleAPIResponse
import com.onusant.apitask.util.Response
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class PropertyRepository(
    private val propertyService: PropertyService,
    private val dao: PropertyDao
) {

    suspend fun syncProperties(onSyncStart: () -> Unit, onSyncFinish: () -> Unit) {
        onSyncStart()
        val response = handleAPIResponse<List<Property>> { propertyService.fetchProperties() }
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

    fun getRecentProperties(recents: List<Int>) : List<Property> {
        return dao.getRecentProperties(recents)
    }

}