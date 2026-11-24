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



val MIGRATION_2_3 = object : Migration(2, 3) {

    override fun migrate(db: SupportSQLiteDatabase) {

        // Create the new Task table with workDate instead of dueDate.
        db.execSQL(
            """
            CREATE TABLE IF NOT EXISTS `Task_new` (
                `id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                `title` TEXT NOT NULL,
                `description` TEXT,
                `priority` TEXT NOT NULL,
                `category` TEXT,
                `workDate` INTEGER,
                `isCompleted` INTEGER NOT NULL,
                `createdAt` INTEGER NOT NULL,
                `updatedAt` INTEGER NOT NULL,
                `serverId` TEXT,
                `syncStatus` TEXT NOT NULL DEFAULT 'PENDING'
            )
            """.trimIndent()
        )

        // Copy existing records and preserve their data.
        db.execSQL(
            """
            INSERT INTO `Task_new` (
                id,
                title,
                description,
                priority,
                category,
                workDate,
                isCompleted,
                createdAt,
                updatedAt,
                serverId,
                syncStatus
            )
            SELECT
                id,
                title,
                description,
                priority,
                category,
                dueDate,
                isCompleted,
                createdAt,
                updatedAt,
                serverId,
                syncStatus
            FROM `Task`
            """.trimIndent()
        )

        // Replace the old table.
        db.execSQL("DROP TABLE `Task`")
        db.execSQL("ALTER TABLE `Task_new` RENAME TO `Task`")
    }
}
