package ir.nilva.reliablemessaging.repository

import ir.nilva.reliablemessaging.network.NetworkService
import okhttp3.ResponseBody
import retrofit2.Response
import javax.inject.Inject

class DataRepository @Inject constructor(){

    @Inject
    lateinit var network :NetworkService

    suspend fun sendMessage(url: String, data: Map<String, String>): Response<ResponseBody> =
        network.sendMessage(url, data)

}