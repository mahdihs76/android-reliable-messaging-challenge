package ir.nilva.reliablemessaging.di

import androidx.work.WorkManager
import dagger.Component
import ir.nilva.reliablemessaging.network.NetworkService
import retrofit2.Retrofit
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class, WorkModule::class])
interface DataProvidersComponent {

    fun getNetwork(): Retrofit

    fun getNetworkService(): NetworkService

    fun getWorkManager(): WorkManager

}