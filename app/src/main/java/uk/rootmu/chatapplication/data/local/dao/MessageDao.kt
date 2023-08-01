package uk.rootmu.chatapplication.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import uk.rootmu.chatapplication.data.local.model.Message

@Dao
interface MessageDao {

    @Insert
    suspend fun insertMessage(message: Message);

    @Query("SELECT * FROM message ORDER BY timestamp ASC")
    fun getAllMessages(): Flow<List<Message>>
}