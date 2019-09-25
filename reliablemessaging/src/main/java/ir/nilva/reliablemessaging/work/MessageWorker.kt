package ir.nilva.reliablemessaging.work

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import ir.nilva.reliablemessaging.KEY_URL
import ir.nilva.reliablemessaging.repository.DataRepository
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject

class MessageWorker(
    context: Context,
    private val workParams: WorkerParameters
) : CoroutineWorker(context, workParams) {

    @Inject
    lateinit var dataRepository: DataRepository

    override suspend fun doWork(): Result = coroutineScope {
        @Suppress("UNCHECKED_CAST")
        val data = workParams.inputData.keyValueMap as HashMap<String, String>

        val response = dataRepository.sendMessage(
            data[KEY_URL] as String,
            data.apply { remove(KEY_URL) }
        )
        if (response.isSuccessful) {
            Result.success()
        } else {
            Result.failure()
        }
    }
}