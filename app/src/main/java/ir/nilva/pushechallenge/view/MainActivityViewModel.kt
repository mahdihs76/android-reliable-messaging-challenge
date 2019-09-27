package ir.nilva.pushechallenge.view

import android.content.Context
import androidx.lifecycle.ViewModel
import ir.nilva.reliablemessaging.ReliableMessaging
import ir.nilva.reliablemessaging.ReliableMessagingListener
import timber.log.Timber
import javax.inject.Inject

const val SERVICE_URL = "https://challenge.ronash.co/reliable-messaging"

class MainActivityViewModel @Inject constructor(
    private val context: Context
) : ViewModel() {

    fun send(data: Map<String, String>) =
        ReliableMessaging
            .with(context)
            .send(SERVICE_URL,
                System.currentTimeMillis(),
                data, object : ReliableMessagingListener {
                    override fun complete(id: Long, numberOfAttempts: Int) {
                        Timber.d("id:$id - numberOfAttempts:$numberOfAttempts")
                    }
                })

}
