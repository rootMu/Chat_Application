package uk.rootmu.chatapplication.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import uk.rootmu.chatapplication.data.remote.model.Choice
import uk.rootmu.chatapplication.data.remote.model.Usage

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