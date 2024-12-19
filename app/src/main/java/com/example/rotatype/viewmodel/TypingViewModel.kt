package com.example.rotatype.viewmodel

import android.os.CountDownTimer
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rotatype.model.TypingSession
import com.example.rotatype.repository.TypingRepository
import kotlinx.coroutines.launch

class TypingViewModel(private val repository: TypingRepository) : ViewModel() {
    private val _currentText = MutableLiveData<String>()
    val currentText: LiveData<String> = _currentText

    private val _wpm = MutableLiveData<Int>()
    val wpm: LiveData<Int> = _wpm

    private val _accuracy = MutableLiveData<Float>()
    val accuracy: LiveData<Float> = _accuracy

    private val _timeLeft = MutableLiveData<Int>()
    val timeLeft: LiveData<Int> = _timeLeft

    private var timer: CountDownTimer? = null
    private var startTime: Long = 0
    private var characterCount: Int = 0

    fun startNewSession(text: String) {
        _currentText.value = text
        startTime = System.currentTimeMillis()
        startTimer()
    }

    private fun startTimer() {
        timer?.cancel()
        timer = object : CountDownTimer(60000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                _timeLeft.value = (millisUntilFinished / 1000).toInt()
            }

            override fun onFinish() {
                saveSession()
            }
        }.start()
    }

    fun processUserInput(input: String) {
        val currentText = _currentText.value ?: return
        characterCount = input.length

        // Calculate accuracy
        var correctChars = 0
        input.forEachIndexed { index, char ->
            if (index < currentText.length && char == currentText[index]) {
                correctChars++
            }
        }

        val accuracyValue = if (characterCount > 0) {
            (correctChars.toFloat() / characterCount) * 100
        } else {
            0f
        }

        // Calculate WPM
        val timeElapsed = (System.currentTimeMillis() - startTime) / 60000f // minutes
        val words = characterCount / 5f // standard word length
        val wpmValue = if (timeElapsed > 0) (words / timeElapsed).toInt() else 0

        _accuracy.value = accuracyValue
        _wpm.value = wpmValue
    }

    private fun saveSession() {
        viewModelScope.launch {
            val session = TypingSession(
                id = 0,
                userId = 1, // Replace with actual user ID
                wpm = _wpm.value ?: 0,
                accuracy = _accuracy.value ?: 0f,
                timestamp = System.currentTimeMillis(),
                textLength = characterCount
            )
            repository.saveTypingSession(session)
        }
    }

    override fun onCleared() {
        super.onCleared()
        timer?.cancel()
    }
}