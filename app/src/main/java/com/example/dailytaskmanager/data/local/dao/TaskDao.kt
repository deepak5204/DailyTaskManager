package com.example.dailytaskmanager.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.dailytaskmanager.data.local.entity.Task
import kotlinx.coroutines.flow.Flow

@Dao

interface TaskDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(task: Task)

    @Update
    suspend fun updateTask(task: Task)

    @Delete
    suspend fun deleteTask(task: Task)

    @Query("SELECT * FROM Task ORDER BY createdAt DESC")
    fun getAllTask(): Flow<List<Task>>

    @Query("select * from Task where id =:taskId")
    suspend fun getTaskById(taskId: Long): Task?

    @Query("DELETE FROM Task")
    suspend fun deleteAllTasks()
}