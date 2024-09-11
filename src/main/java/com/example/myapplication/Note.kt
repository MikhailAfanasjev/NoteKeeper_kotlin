package com.example.myapplication

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Notes") // Сущность таблицы "Notes"
data class Note(
    var title: String, // Заголовок заметки
    var description: String, // Описание заметки
    @PrimaryKey(autoGenerate = true) // Автоматически генерируемый первичный ключ
var id: Int = 0
) {
    override fun toString(): String { // Возвращает заголовок как строку
        return title
    }
}