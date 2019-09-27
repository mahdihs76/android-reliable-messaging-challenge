package ir.nilva.reliablemessaging.di

import android.content.Context
import androidx.work.WorkManager
import dagger.Module
import dagger.Provides
import ir.nilva.reliablemessaging.ApplicationContext
import ir.nilva.reliablemessaging.network.*
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
open class LibraryModule {

    @Provides
    fun provideContext(): Context {
        return ApplicationContext.context
    }

    @Singleton
    @Provides
    open fun provideRetrofit(
        converter: GsonConverterFactory,
        httpClient: OkHttpClient.Builder,
        @Named("baseURL") baseUrl: String
    ): Retrofit = NetworkServiceGenerator(converter, httpClient, baseUrl).getClient()

    @Singleton
    @Provides
    fun provideOkHttp(interceptor: HttpLoggingInterceptor): OkHttpClient.Builder =
        OkHttpClient().newBuilder().apply {
            connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
            readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
            callTimeout(CALL_TIMEOUT, TimeUnit.SECONDS)
            addInterceptor(interceptor)
        }

    @Singleton
    @Provides
    fun provideHttpLoggingInterceptor(logger: HttpLoggingInterceptor.Logger)
            : HttpLoggingInterceptor = HttpLoggingInterceptor(logger).apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    @Singleton
    @Provides
    fun provedHttpLogger(): HttpLoggingInterceptor.Logger =
        HttpLoggingInterceptor.Logger {
            Timber.tag("OkHttp").d(it)
        }

    @Singleton
    @Provides
    @Named("baseURL")
    fun provideBaseURL() = BASE_URL

    @Singleton
    @Provides
    fun provideGsonConverterFactory(): GsonConverterFactory =
        GsonConverterFactory.create()

    @Singleton
    @Provides
    fun getNetworkService(retrofit: Retrofit): NetworkService =
        retrofit.create(NetworkService::class.java)

    @Singleton
    @Provides
    fun provideWorkManager(context: Context): WorkManager =
        WorkManager.getInstance(context)
    
}