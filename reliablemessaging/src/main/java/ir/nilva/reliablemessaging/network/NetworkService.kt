package ir.nilva.reliablemessaging.network

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import retrofit2.http.Url

interface NetworkService {

    @POST
    @FormUrlEncoded
    suspend fun sendMessage(
        @Url url : String,
        @FieldMap message: Map<String, String>
    ): Call<ResponseBody>

}