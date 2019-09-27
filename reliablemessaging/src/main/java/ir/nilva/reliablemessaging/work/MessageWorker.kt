package ir.nilva.reliablemessaging.work

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import ir.nilva.reliablemessaging.KEY_URL
import ir.nilva.reliablemessaging.di.DaggerLibraryComponent
import kotlinx.coroutines.coroutineScope
import timber.log.Timber

class MessageWorker(
    context: Context,
    private val workParams: WorkerParameters
) : CoroutineWorker(context, workParams) {

    @Suppress("UNCHECKED_CAST")
    override suspend fun doWork(): Result = coroutineScope {
        Timber.d("WORK --->>> START")

        val data = (workParams.inputData.keyValueMap as Map<String, String>).toMutableMap()
        val url = workParams.inputData.getString(KEY_URL) ?: ""

        val response = DaggerLibraryComponent.create().getRepository().sendMessage(url, data.apply { remove(KEY_URL) })
        Timber.d("WORK --->>> WORKING")

        if (response.isSuccessful) {
            Timber.d("WORK --->>> SUCCESS")
            Result.success()
        } else {
            Timber.d("WORK --->>> FAIL")
            Result.retry()
        }
    }
}