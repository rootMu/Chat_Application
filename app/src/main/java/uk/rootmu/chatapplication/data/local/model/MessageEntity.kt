package uk.rootmu.chatapplication.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "message")
data class MessageEntity(
    val content: String,
    val sender: String,
    val recipient: String,
    val timestamp: Long
) {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}