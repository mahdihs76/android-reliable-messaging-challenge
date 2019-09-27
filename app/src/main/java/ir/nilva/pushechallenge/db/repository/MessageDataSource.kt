package ir.nilva.pushechallenge.db.repository

import android.content.Context
import ir.nilva.pushechallenge.db.data.Message
import ir.nilva.pushechallenge.db.data.MessageDao
import ir.nilva.reliablemessaging.ReliableMessaging
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

const val SERVICE_URL = "https://challenge.ronash.co/reliable-messaging"

class MessageDataSource @Inject constructor(
    private val context: Context,
    private val messageDao: MessageDao
) : MessageRepository {

    override fun findById(id: Long) = messageDao.findById(id)

    override fun findAll() = messageDao.findAll()

    override suspend fun insert(message: Message) = messageDao.insert(message)

    override suspend fun delete(id: Long) = messageDao.delete(id)

    override fun send(message: Message) =
        ReliableMessaging
            .with(context)
            .send(
                SERVICE_URL,
                message.id,
                hashMapOf("data" to message.data)
            ) { id, numberOfAttempts, isSuccessful ->
                CoroutineScope(Dispatchers.IO).launch {
                    insert(Message(id, message.data, numberOfAttempts, isSuccessful))
                }
            }
}