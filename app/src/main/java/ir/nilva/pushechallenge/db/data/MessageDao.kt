package ir.nilva.pushechallenge.db.data

import androidx.lifecycle.LiveData
import androidx.room.*


@Dao
interface MessageDao {
    @Query("SELECT * FROM Message WHERE id=:id")
    fun findById(id: Long): LiveData<Message>

    @Query("SELECT * FROM Message")
    fun findAll(): LiveData<List<Message>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(message: Message): Long

    @Query("DELETE FROM Message WHERE id=:id")
    suspend fun delete(id: Long): Int

}