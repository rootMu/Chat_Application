package uk.rootmu.chatapplication.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "generated_answer")
data class GeneratedAnswer(
    @PrimaryKey
    val id: String,
    val choices: List<Choice>,
    val created: Int,
    val model: String,
    val `object`: String,
    val usage: Usage
)