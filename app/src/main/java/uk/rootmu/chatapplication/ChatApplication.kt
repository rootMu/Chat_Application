package uk.rootmu.chatapplication

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import uk.rootmu.chatapplication.di.appModule

class ChatApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@ChatApplication)
            modules(appModule)
        }
    }
}