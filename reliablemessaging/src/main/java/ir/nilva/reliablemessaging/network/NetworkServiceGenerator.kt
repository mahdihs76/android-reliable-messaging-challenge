package ir.nilva.reliablemessaging.network

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

class NetworkServiceGenerator @Inject constructor(
    private val converter: GsonConverterFactory,
    private val httpClient: OkHttpClient.Builder,
    private val baseUrl: String
) {

    fun getClient(): Retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(converter)
        .client(httpClient.build())
        .build()
}