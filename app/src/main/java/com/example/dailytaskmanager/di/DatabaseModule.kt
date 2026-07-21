package com.example.dailytaskmanager.di


import android.content.Context
import androidx.room.Room

import com.example.dailytaskmanager.data.local.dao.TaskDao
import com.example.dailytaskmanager.data.local.database.TaskDatabase

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)

object DatabaseModule {

    @Provides
    @Singleton
    fun provideTaskDatabase(
        @ApplicationContext context: Context
    ): TaskDatabase {

        val db = Room.databaseBuilder(
            context = context,
            klass = TaskDatabase::class.java,
            name = "database-name"
        ).build()
        return db
    }

    @Provides
    fun provideTaskDao(
        database: TaskDatabase
    ) : TaskDao {
        return database.taskDao()
    }
}