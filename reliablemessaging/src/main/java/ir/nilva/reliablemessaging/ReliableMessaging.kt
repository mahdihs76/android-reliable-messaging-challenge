package ir.nilva.reliablemessaging

import android.content.Context

class ReliableMessaging {

    companion object {
        fun with(context: Context): ReliableMessagingHandler {
            ApplicationContext.initialize(context)
            return ReliableMessagingHandler()
        }
    }

}


