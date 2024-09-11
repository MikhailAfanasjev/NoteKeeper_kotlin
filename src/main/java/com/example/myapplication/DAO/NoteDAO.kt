package com.example.myapplication.DAO

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.myapplication.Note

@Dao
interface NoteDAO {
    @Insert
    fun insert(note: Note)

    @Query("SELECT * FROM Notes ORDER BY id DESC")
    fun getAllNotes(): List<Note>

    @Delete
    fun delete(note: Note)
}