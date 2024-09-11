package com.example.myapplication

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.DAO.NoteDAO
import com.example.myapplication.DAO.NoteDB
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors


class MainActivity : AppCompatActivity() {

    private lateinit var noteAdapter: NoteAdapter
    private lateinit var recyclerViewNotes: RecyclerView
    private lateinit var editTitle: EditText
    private lateinit var editDescription: EditText
    private lateinit var executorService : ExecutorService
    private lateinit var noteDAO: NoteDAO
    private lateinit var noteDB: NoteDB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editTitle = findViewById(R.id.editTitle)
        editDescription = findViewById(R.id.editDescription)
        val buttonSave: Button = findViewById(R.id.buttonSave)
        recyclerViewNotes = findViewById(R.id.recyclerViewNotes)

        noteDB = NoteDB.getDatabase(this)
        noteDAO = noteDB.noteDAO()
        executorService = Executors.newSingleThreadExecutor()
        noteAdapter = NoteAdapter(ArrayList(), this)
        recyclerViewNotes.adapter = noteAdapter
        recyclerViewNotes.layoutManager = LinearLayoutManager(this)

        loadNotes()

        buttonSave.setOnClickListener {
            val title = editTitle.text.toString()
            val description = editDescription.text.toString()

            if (title.isNotEmpty() && description.isNotEmpty()) {
                val newNote = Note(title, description)
                executorService.execute {
                    noteDAO.insert(newNote)
                    loadNotes()
                }
                editTitle.text.clear()
                editDescription.text.clear()
            } else {
                Toast.makeText(this, "Пожалуйста, введите заголовок и описание", Toast.LENGTH_SHORT).show()
            }
        }

    // Настройка жестов для удаления заметок
    ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
        override fun onMove(@NonNull recyclerView:  RecyclerView,@NonNull viewHolder: RecyclerView.ViewHolder,@NonNull target: RecyclerView.ViewHolder): Boolean {
            return false // Перемещение не поддерживается
        }

        override fun onSwiped(@NonNull viewHolder:  RecyclerView.ViewHolder, direction: Int) {
            val position = viewHolder.adapterPosition // Получаем позицию смахнутого элемента
            val noteToRemove = noteAdapter.getNoteAtPosition(position) // Получаем соответствующую заметку
            executorService.execute {
                noteDAO.delete(noteToRemove)
                loadNotes()
            }
        }
    })
    .attachToRecyclerView(recyclerViewNotes)
}

    private fun loadNotes() {
        executorService.execute {
            val notes = noteDAO.getAllNotes()
            runOnUiThread {
                noteAdapter.updateNotes(notes)
            }
        }
    }
}