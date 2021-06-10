package my.project.myprojectfinal.todolist.db.todolist

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface CatatanharianDao {
    @Query("Select * from catatan_harian")
    fun getTodos(): LiveData<List<Catatanharian>>

    @Query("Select * from catatan_harian ORDER BY waktu_dibuat_millis")
    fun getSortDibuat() : LiveData<List<Catatanharian>>

    @Query("Select * from catatan_harian ORDER BY tempo_millis")
    fun getSortTempo(): LiveData<List<Catatanharian>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertTodo(todo: Catatanharian)

    @Delete
    suspend fun deleteTodo(todo: Catatanharian)

    @Update
    suspend fun updateTodo(todo: Catatanharian)
}