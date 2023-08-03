package uk.rootmu.chatapplication.data.remote.model

import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

data class GeneratedAnswer(
    @SerializedName("choices")
    val choices: List<Choice>,
    @SerializedName("created")
    val created: Int,
    @PrimaryKey
    @SerializedName("id")
    val id: String,
    @SerializedName("model")
    val model: String,
    @SerializedName("object")
    val `object`: String,
    @SerializedName("usage")
    val usage: Usage
)