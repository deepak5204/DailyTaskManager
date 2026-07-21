package com.example.dailytaskmanager.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.dailytaskmanager.data.local.converter.Converters
import com.example.dailytaskmanager.data.local.dao.TaskDao
import com.example.dailytaskmanager.data.local.entity.Task

// Generate a SQLite database for me
//Instead of writing SQL manually, Room generates everything.
@Database(
    entities = [Task::class],
    version = 1,
    exportSchema = false
)

@TypeConverters(Converters::class)
abstract class TaskDatabase: RoomDatabase() {
    abstract fun taskDao() : TaskDao
}