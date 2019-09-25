package ir.nilva.reliablemessaging.repository

import ir.nilva.reliablemessaging.di.DaggerDataProvidersComponent
import ir.nilva.reliablemessaging.di.NetworkModule
import ir.nilva.reliablemessaging.network.NetworkService
import okhttp3.ResponseBody
import retrofit2.Response
import javax.inject.Inject

class DataRepository @Inject constructor() {

    private var network: NetworkService

    init {
        val component = DaggerDataProvidersComponent.builder()
            .networkModule(NetworkModule()).build()

        network = component.getNetworkService()
    }


    suspend fun sendMessage(url: String, data: Map<String, String>): Response<ResponseBody> =
        network.sendMessage(url, data)

}