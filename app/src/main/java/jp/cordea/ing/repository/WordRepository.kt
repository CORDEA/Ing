package jp.cordea.ing.repository

import jp.cordea.ing.Word
import jp.cordea.ing.api.WordApi
import jp.cordea.ing.api.WordRequest
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WordRepository @Inject constructor(
    private val api: WordApi
) {
    suspend fun findAll(): List<Word> {
        val response = api.getWords(WordRequest("doGet"))
        response.error?.let {
            throw IllegalStateException(it.message)
        }
        return response.response.result
    }
}
