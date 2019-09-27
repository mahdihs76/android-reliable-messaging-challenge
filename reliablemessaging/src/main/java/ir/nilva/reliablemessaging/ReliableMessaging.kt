package ir.nilva.reliablemessaging

import android.content.Context
import ir.nilva.reliablemessaging.di.DaggerLibraryComponent
import ir.nilva.reliablemessaging.di.LibraryComponent
import ir.nilva.reliablemessaging.di.NetworkModule
import ir.nilva.reliablemessaging.di.WorkModule


object ReliableMessaging {

    lateinit var component: LibraryComponent

    fun with(context: Context): ReliableMessagingManager {
        ApplicationContext.initialize(context)
        initDaggerComponent()
        return ReliableMessagingManager()
    }

    private fun initDaggerComponent() {
        component = DaggerLibraryComponent
            .builder()
            .networkModule(NetworkModule())
            .workModule(WorkModule())
            .build()
    }
}



