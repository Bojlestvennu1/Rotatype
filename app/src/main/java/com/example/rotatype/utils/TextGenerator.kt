package com.example.rotatype.utils

object TextGenerator {
    private val texts = listOf(
        "Быстрая коричневая лиса прыгает через ленивую собаку",
        "Программирование - это искусство создания элегантных решений",
        "Практика - путь к совершенству в печати"
    )

    fun getRandomText(): String {
        return texts.random()
    }
}