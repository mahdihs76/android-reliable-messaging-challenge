package ir.nilva.pushechallenge.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ir.nilva.pushechallenge.db.data.Message
import ir.nilva.pushechallenge.db.repository.MessageRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainActivityViewModel @Inject constructor(
    private val repository: MessageRepository
) : ViewModel() {

    fun getMessages() = repository.findAll()

    fun delete(id: Long) = viewModelScope.launch {
        repository.delete(id)
    }

    fun send(data: String) = repository.send(Message(System.currentTimeMillis(), data))

}
