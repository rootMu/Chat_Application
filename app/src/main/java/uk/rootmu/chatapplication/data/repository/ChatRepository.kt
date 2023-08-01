package uk.rootmu.chatapplication.data.repository

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import uk.rootmu.chatapplication.data.local.dao.MessageDao
import uk.rootmu.chatapplication.data.local.model.Message

class ChatRepository(private val messageDao: MessageDao, private val defaultDispatcher: CoroutineDispatcher = Dispatchers.Default) {

    suspend fun insertMessage(message: Message) {
        withContext(defaultDispatcher) {
            messageDao.insertMessage(message.copy(timeStamp = System.currentTimeMillis()))
        }
    }

    fun getAllMessages(): Flow<List<Message>> {
        return messageDao.getAllMessages()
    }
}