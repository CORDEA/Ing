package jp.cordea.ing.api

import jp.cordea.ing.BuildConfig
import retrofit2.http.Body
import retrofit2.http.POST

interface WordApi {
    @POST("/v1/scripts/${BuildConfig.SCRIPT_ID}:run")
    suspend fun getWords(@Body request: WordRequest): WordResponse
}
