package com.onusant.apitask.util

import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class Network {

    companion object {

        suspend inline fun <reified T: Any> handleAPIResponse(
            crossinline execute: suspend () -> HttpResponse
        ) : Response<T> {

            return try {
                val response = withContext(Dispatchers.IO) { execute() }
                val result = response.body<T>()
                Response.Success(result)
            } catch (e: ClientRequestException) {
                Response.Failure(Error(e.response.bodyAsText(), e))
            }  catch (e: ServerResponseException) {
                Response.Failure(Error("${e.response.status} Server connection failed", e))
            } catch (e: Exception) {
                if (e is CancellationException) throw e
                Response.Failure(Error("Something went wrong", e))
            }
        }

    }
}