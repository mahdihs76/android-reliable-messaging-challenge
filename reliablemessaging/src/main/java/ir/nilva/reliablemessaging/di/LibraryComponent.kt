package ir.nilva.reliablemessaging.di

import androidx.work.WorkManager
import dagger.Component
import ir.nilva.reliablemessaging.ReliableMessagingManager
import ir.nilva.reliablemessaging.network.NetworkService
import ir.nilva.reliablemessaging.work.MessageWorker
import retrofit2.Retrofit
import javax.inject.Singleton

@Singleton
@Component(modules = [LibraryModule::class])
interface LibraryComponent {

    fun getNetwork(): Retrofit

    fun getNetworkService(): NetworkService

    fun getWorkManager(): WorkManager

    fun inject(manager: ReliableMessagingManager)

    fun inject(worker: MessageWorker)

}