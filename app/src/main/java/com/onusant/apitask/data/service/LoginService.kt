package com.onusant.apitask.data.service

import io.ktor.client.HttpClient
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlinx.serialization.Serializable

@Serializable
data class LoginRequest(
    val identifier: String,
    val password: String
)

class LoginService(private val client: HttpClient) {

    suspend fun login(loginRequest: LoginRequest) : HttpResponse {
        return client.post("https://a.khelogame.xyz/login") {
            contentType(ContentType.Application.Json)
            setBody(loginRequest)
        }
    }
}