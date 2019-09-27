package ir.nilva.pushechallenge.db.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Message(
    @PrimaryKey val id: Long,
    val data: String,
    val sendAttempts: Int = 0,
    val isSuccessful: Boolean = false
)
