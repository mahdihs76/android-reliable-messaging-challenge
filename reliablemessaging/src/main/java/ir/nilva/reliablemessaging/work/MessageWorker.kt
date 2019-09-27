package ir.nilva.reliablemessaging.work

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import ir.nilva.reliablemessaging.KEY_URL
import ir.nilva.reliablemessaging.ReliableMessaging
import ir.nilva.reliablemessaging.network.NetworkService
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject

class MessageWorker(
    context: Context,
    private val workParams: WorkerParameters
) : CoroutineWorker(context, workParams) {

    init {
        ReliableMessaging.component.inject(this)
    }

    @Inject
    lateinit var networkService: NetworkService

    @Suppress("UNCHECKED_CAST")
    override suspend fun doWork(): Result = coroutineScope {

        val data =
            (workParams.inputData.keyValueMap as Map<String, String>).toMutableMap()

        val url = workParams.inputData.getString(KEY_URL) ?: ""

        val response =
            networkService.sendMessage(url, data.apply { remove(KEY_URL) })

        if (response.isSuccessful) {
            Result.success()
        } else {
            Result.retry()
        }
    }
}