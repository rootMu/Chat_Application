package uk.rootmu.chatapplication.data.remote.model

import com.google.gson.annotations.SerializedName

data class Message (
    @SerializedName("role")
    val role: String = Role.USER.role,
    @SerializedName("content")
    val content: String
)

enum class Role(val role: String) {
    SYSTEM("system"),
    USER("user"),
    ASSISTANT("assistant"),
    FUNCTION("function")
}
