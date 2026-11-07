package com.example.dailytaskmanager.data.local.database

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

val MIGRATION_1_2 = object : Migration(1, 2) {

    override fun migrate(db: SupportSQLiteDatabase) {

        db.execSQL(
            "ALTER TABLE `Task` ADD COLUMN `serverId` TEXT"
        )

        db.execSQL(
            """
            ALTER TABLE `Task`
            ADD COLUMN `syncStatus` TEXT NOT NULL DEFAULT 'PENDING'
            """.trimIndent()
        )
    }
}