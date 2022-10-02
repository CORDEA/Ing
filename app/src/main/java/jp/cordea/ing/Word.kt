package jp.cordea.ing

import kotlinx.serialization.Serializable

@Serializable
data class Word(
    val id: Int,
    val question: String,
    val answer: String
)
