package com.example.dailytaskmanager.data.local.converter

import androidx.room.TypeConverter
import com.example.dailytaskmanager.data.local.entity.Priority
import com.example.dailytaskmanager.data.local.entity.SyncStatus

class Converters {

    @TypeConverter
    fun fromPriority(priority: Priority) : String {
        return priority.name
    }

    @TypeConverter
    fun toPriority(priority: String): Priority {
        return Priority.valueOf(priority)
    }

    @TypeConverter
    fun fromSyncStatus(status: SyncStatus): String {
        return status.name
    }

    @TypeConverter
    fun toSyncStatus(status: String): SyncStatus {
        return SyncStatus.valueOf(status)
    }
}