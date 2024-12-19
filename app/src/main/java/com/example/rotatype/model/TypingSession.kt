package com.example.rotatype.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ForeignKey

@Entity(
    tableName = "typingsession",
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = ["id"],
            childColumns = ["userId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class TypingSession(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val userId: Int,
    val wpm: Int,
    val accuracy: Float,
    val timestamp: Long,
    val textLength: Int
)