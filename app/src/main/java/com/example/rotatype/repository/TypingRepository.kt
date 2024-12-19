package com.example.rotatype.repository

import com.example.rotatype.data.AppDatabase
import com.example.rotatype.model.TypingSession
import com.example.rotatype.model.User
import kotlinx.coroutines.flow.Flow

class TypingRepository(private val db: AppDatabase) {
    private val userDao = db.userDao()
    private val sessionDao = db.typingSessionDao()

    suspend fun saveTypingSession(session: TypingSession) {
        sessionDao.insert(session)
    }

    fun getUserStats(userId: Int): Flow<List<TypingSession>> {
        return sessionDao.getUserSessions(userId)
    }

    suspend fun saveUser(user: User) {
        userDao.insert(user)
    }

    fun getUser(userId: Int): Flow<User> {
        return userDao.getUser(userId)
    }
}