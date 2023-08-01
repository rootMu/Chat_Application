package uk.rootmu.chatapplication.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import uk.rootmu.chatapplication.data.local.dao.MessageDao
import uk.rootmu.chatapplication.data.local.model.Message

@Database(entities = [Message::class], version = 1, exportSchema = false)
abstract class ChatDatabase : RoomDatabase() {
    abstract fun messageDao(): MessageDao
}