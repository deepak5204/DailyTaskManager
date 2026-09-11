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

    @Query("""
        SELECT * FROM Task ORDER BY isCompleted ASC , 
        CASE priority 
            WHEN 'HIGH' THEN 1 
            WHEN 'MEDIUM' THEN 2 
            WHEN 'LOW' THEN 3 
        END ASC, 
        createdAt DESC
    """)
    fun getAllTasks(): Flow<List<Task>>


    @Query("DELETE FROM Task")
    suspend fun deleteAllTasks()

    @Query("SELECT * FROM Task WHERE id = :id")
    fun getTaskById(id: Long): Flow<Task?>


    @Query("DELETE FROM Task WHERE createdAt < :cutoffTime AND isCompleted = 1")
    suspend fun deleteTasksOlderThan(cutoffTime: Long) : Int
}