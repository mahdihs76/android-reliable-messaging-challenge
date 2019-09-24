package ir.nilva.reliablemessaging.di

import dagger.Module
import dagger.Provides
import ir.nilva.reliablemessaging.network.*
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
class NetworkModule {

    @Singleton
    @Provides
    fun provideRetrofit(
        converter: GsonConverterFactory,
        httpClient: OkHttpClient.Builder,
        @Named("baseUrl") baseUrl: String
    ) = NetworkServiceGenerator(converter, httpClient, baseUrl)
        .getClient()

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
    fun provedHttpLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
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
    fun getMainInterface(retrofit: Retrofit): NetworkService =
        retrofit.create(NetworkService::class.java)

}