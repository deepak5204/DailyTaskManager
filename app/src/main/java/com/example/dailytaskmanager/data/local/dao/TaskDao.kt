package com.example.dailytaskmanager.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.dailytaskmanager.data.local.entity.Task
import kotlinx.coroutines.flow.Flow

@Dao

interface TaskDao {
    @Insert
    suspend fun insertTask(task: Task): Long

    @Update
    suspend fun updateTask(task: Task)

    @Delete
    suspend fun deleteTask(task: Task)

    @Query(
        """
        SELECT * FROM Task  
        WHERE syncStatus != 'DELETE_PENDING' 
        ORDER BY isCompleted ASC , 
        CASE priority 
            WHEN 'HIGH' THEN 1 
            WHEN 'MEDIUM' THEN 2 
            WHEN 'LOW' THEN 3 
        END ASC, 
        createdAt DESC
    """
    )
    fun getAllTasks(): Flow<List<Task>>


    @Query("DELETE FROM Task")
    suspend fun deleteAllTasks()

    @Query("SELECT * FROM Task WHERE id = :id")
    fun getTaskById(id: Long): Flow<Task?>


    @Query("DELETE FROM Task WHERE createdAt < :cutoffTime AND isCompleted = 1")
    suspend fun deleteTasksOlderThan(cutoffTime: Long): Int

    @Query(
        """
    UPDATE Task
    SET serverId = :serverId,
        syncStatus = 'SYNCED'
    WHERE id = :localId
"""
    )
    suspend fun markTaskAsSynced(
        localId: Long, serverId: String
    )

    @Query("SELECT * FROM Task WHERE syncStatus = 'PENDING'")
    suspend fun getPendingTasks(): List<Task>


    @Query("SELECT * FROM Task WHERE serverId = :serverId LIMIT 1")
    suspend fun getTaskByServerId(serverId: String): Task?


    // update an existing task using its server ID.
    @Query(
        """
    UPDATE Task
    SET title = :title,
        description = :description,
        priority = :priority,
        category = :category,
        dueDate = :dueDate,
        isCompleted = :isCompleted,
        createdAt = :createdAt,
        updatedAt = :updatedAt,
        syncStatus = 'SYNCED'
    WHERE serverId = :serverId AND syncStatus = 'SYNCED'
"""
    )
    suspend fun updateTaskFromServer(
        serverId: String,
        title: String,
        description: String?,
        priority: String,
        category: String?,
        dueDate: Long?,
        isCompleted: Boolean,
        createdAt: Long,
        updatedAt: Long
    )


    @Query("SELECT * FROM Task WHERE syncStatus = 'DELETE_PENDING'")
    suspend fun getTasksPendingDeletion(): List<Task>


    @Query(
        """
    UPDATE Task
    SET syncStatus = 'DELETE_PENDING'
    WHERE id = :localId
    AND serverId IS NOT NULL
    """
    )
    suspend fun markTaskForDeletion(localId: Long)

    @Query("DELETE FROM Task WHERE id = :localId")
    suspend fun permanentlyDeleteTask(localId: Long)

}