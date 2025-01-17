package ir.nilva.reliablemessaging

import androidx.lifecycle.Observer
import androidx.work.*
import ir.nilva.reliablemessaging.work.MessageWorker
import java.util.concurrent.TimeUnit
import javax.inject.Inject

const val KEY_URL = "KEY_URL"
const val BACK_OFF_DELAY = WorkRequest.MIN_BACKOFF_MILLIS

class ReliableMessagingManager {

    @Inject
    lateinit var workManager: WorkManager

    init {
        ReliableMessaging.component.inject(this)
    }

    fun send(
        url: String, id: Long, data: Map<String, String>,
        listener: (Long, Int, Boolean) -> Unit
    ) {
        val workRequest =
            OneTimeWorkRequestBuilder<MessageWorker>()
                .setInputData(
                    Data.Builder()
                        .putString(KEY_URL, url)
                        .putAll(data)
                        .build()
                ).setBackoffCriteria(
                    BackoffPolicy.EXPONENTIAL,
                    BACK_OFF_DELAY,
                    TimeUnit.MILLISECONDS
                )
                .setConstraints(prepareWorkConstraints())
                .build()

        workManager.enqueue(workRequest)

        workManager.getWorkInfoByIdLiveData(workRequest.id).apply {
            observeForever(object : Observer<WorkInfo> {
                override fun onChanged(workInfo: WorkInfo?) {
                    workInfo ?: return
                    if (workInfo.state == WorkInfo.State.SUCCEEDED) {
                        listener(id, workInfo.runAttemptCount, true)
                        removeObserver(this)
                    } else {
                        listener(id, workInfo.runAttemptCount, false)
                    }
                }
            })
        }
    }

    private fun prepareWorkConstraints() =
        Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()
}