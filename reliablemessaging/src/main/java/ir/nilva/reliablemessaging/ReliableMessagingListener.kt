package ir.nilva.reliablemessaging

interface ReliableMessagingListener {
    fun complete(id: Long, numberOfAttempts: Int)
}