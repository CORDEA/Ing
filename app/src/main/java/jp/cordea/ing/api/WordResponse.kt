package jp.cordea.ing.api

import kotlinx.serialization.Serializable

@Serializable
data class WordResponse(
    val done: Boolean,
    val error: Status? = null,
    val response: Response = Response("")
) {
    @Serializable
    data class Status(val code: Int, val message: String)

    @Serializable
    data class Response(val result: String)
}
