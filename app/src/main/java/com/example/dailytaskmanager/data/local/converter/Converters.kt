package com.example.dailytaskmanager.data.local.converter

import androidx.room.TypeConverters
import com.example.dailytaskmanager.data.local.entity.Priority

class Converters {

    @TypeConverters
    fun fromPriority(priority: Priority) : String {
        return priority.name
    }

    @TypeConverters
    fun toPriority(priority: String): Priority {
        return Priority.valueOf(priority)
    }
}