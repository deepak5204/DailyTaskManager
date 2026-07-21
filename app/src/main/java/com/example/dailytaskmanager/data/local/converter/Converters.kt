package com.example.dailytaskmanager.data.local.converter

import androidx.room.TypeConverter
import com.example.dailytaskmanager.data.local.entity.Priority

class Converters {

    @TypeConverter
    fun fromPriority(priority: Priority) : String {
        return priority.name
    }

    @TypeConverter
    fun toPriority(priority: String): Priority {
        return Priority.valueOf(priority)
    }
}