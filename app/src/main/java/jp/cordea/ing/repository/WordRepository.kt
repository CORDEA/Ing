package jp.cordea.ing.repository

import jp.cordea.ing.Word
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WordRepository @Inject constructor() {
    suspend fun findAll(): List<Word> {
        return emptyList()
    }
}
