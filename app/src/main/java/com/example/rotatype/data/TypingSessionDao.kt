package com.example.rotatype.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.rotatype.model.TypingSession
import kotlinx.coroutines.flow.Flow

@Dao
interface TypingSessionDao {
    @Query("SELECT * FROM typingsession WHERE userId = :userId")
    fun getUserSessions(userId: Int): Flow<List<TypingSession>>

    @Insert
    suspend fun insert(session: TypingSession)
}
