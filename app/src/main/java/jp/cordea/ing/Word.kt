package jp.cordea.ing

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Word(
    val id: Int,
    val question: String,
    val answer: String,
    val category: WordCategory,
    val link: String
)

@Serializable
enum class WordCategory {
    @SerialName("word_book")
    WORD_BOOK,
    @SerialName("game")
    GAME,
    @SerialName("novel")
    NOVEL
}
