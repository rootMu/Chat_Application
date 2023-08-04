package uk.rootmu.chatapplication.data.remote.model

import com.google.gson.annotations.SerializedName
import uk.rootmu.chatapplication.data.remote.Models

data class RequestBody(
    @SerializedName("model")
    val model: String = Models.GPT35.family,
    @SerializedName("messages")
    val messages: List<Message>,
//    @SerializedName("max_tokens")
//    val maxTokens: Int = 100,
//    @SerializedName("temperature")
//    var temperature: Float = 0f,
//    @SerializedName("top_p")
//    val topP: Float = 1f,
//    @SerializedName("frequency_penalty")
//    val frequencyPenalty: Int = 0,
//    @SerializedName("presence_penalty")
//    val presencePenalty: Int = 0,
//    @SerializedName("stop")
//    val stop: String = "\n"
)