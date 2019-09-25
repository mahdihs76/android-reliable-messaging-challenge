package ir.nilva.reliablemessaging.work

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import ir.nilva.reliablemessaging.KEY_DATA
import kotlinx.coroutines.coroutineScope

class MessageWorker(
    context: Context,
    private val workParams: WorkerParameters
) : CoroutineWorker(context, workParams) {

    override suspend fun doWork(): Result = coroutineScope {
        val data = workParams.inputData.getString(KEY_DATA)
        Result.success()
    }
}