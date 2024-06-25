package com.onusant.apitask.data.service

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse

class PropertyService(private val client: HttpClient) {

    suspend fun fetchProperties() : HttpResponse {
        return client.get("https://a.khelogame.xyz/get-properties")
    }

}