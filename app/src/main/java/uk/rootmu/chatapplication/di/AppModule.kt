package uk.rootmu.chatapplication.di

import androidx.room.Room
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import uk.rootmu.chatapplication.data.local.database.ChatDatabase
import uk.rootmu.chatapplication.data.repository.ChatRepository
import uk.rootmu.chatapplication.ui.viewmodels.ChatViewModel

val appModule = module {
    single {
        Room.databaseBuilder(
            androidApplication(),
            ChatDatabase::class.java,
            "chat_database"
        ).build()
    }

    single { get<ChatDatabase>().messageDao() }

    single { ChatRepository(get(), get(), Dispatchers.IO) }

    viewModel { ChatViewModel(get(), Dispatchers.IO ) }
}