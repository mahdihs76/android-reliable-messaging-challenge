package ir.nilva.reliablemessaging

import androidx.lifecycle.Observer
import androidx.work.*
import ir.nilva.reliablemessaging.work.MessageWorker
import java.util.concurrent.TimeUnit
import javax.inject.Inject

const val KEY_DATA = "KEY_DATA"
const val BACK_OFF_DELAY = 2L

class ReliableMessagingHandler {

    @Inject
    lateinit var workManager: WorkManager

    fun send(
        id: Long, data: String,
        listener: ReliableMessagingListener
    ) {

        val workRequest =
            OneTimeWorkRequestBuilder<MessageWorker>()
                .setInputData(
                    workDataOf(
                        KEY_DATA to data
                    )
                ).setBackoffCriteria(
                    BackoffPolicy.EXPONENTIAL,
                    BACK_OFF_DELAY,
                    TimeUnit.SECONDS
                )
                .setConstraints(prepareWorkConstraints())
                .build()

        workManager.enqueue(workRequest)

        workManager.getWorkInfoByIdLiveData(workRequest.id).apply {
            observeForever(object : Observer<WorkInfo> {
                override fun onChanged(workInfo: WorkInfo?) {
                    workInfo?.takeIf {
                        workInfo.state == WorkInfo.State.SUCCEEDED
                    }?.let {
                        listener.complete(id, workInfo.runAttemptCount)
                        removeObserver(this)
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