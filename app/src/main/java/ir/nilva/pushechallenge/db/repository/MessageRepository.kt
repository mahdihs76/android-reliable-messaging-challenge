package ir.nilva.pushechallenge.db.repository

import androidx.lifecycle.LiveData
import ir.nilva.pushechallenge.db.data.Message

interface MessageRepository {
    fun findById(id: Long): LiveData<Message>

    fun findAll(): LiveData<List<Message>>

    suspend fun insert(message: Message): Long

    suspend fun delete(id: Long): Int

    fun send(message: Message)
}
