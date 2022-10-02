package jp.cordea.ing.api

import jp.cordea.ing.Word
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.descriptors.element
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.encoding.decodeStructure
import kotlinx.serialization.json.Json

@Serializable
data class WordResponse(
    val done: Boolean,
    val error: Status? = null,
    val response: Response = Response(emptyList())
) {
    @Serializable
    data class Status(val code: Int, val message: String)

    @Serializable(with = ResponseSerializer::class)
    data class Response(val result: List<Word>)
}

class ResponseSerializer : KSerializer<WordResponse.Response> {
    override val descriptor: SerialDescriptor = buildClassSerialDescriptor("Response") {
        element<List<Word>>("result")
    }

    override fun deserialize(decoder: Decoder): WordResponse.Response {
        val result = decoder.decodeStructure(descriptor) {
            val index = decodeElementIndex(descriptor)
            decodeStringElement(descriptor, index)
        }
        return WordResponse.Response(Json.decodeFromString(result))
    }

    override fun serialize(encoder: Encoder, value: WordResponse.Response) {
        throw NotImplementedError()
    }
}
