package com.example.myapplication

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R

class NoteAdapter(private val noteList: ArrayList<Note>, private val context: Context) :
    RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.fragment_note_detail, parent, false)
        return NoteViewHolder(view)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val note = noteList[position]
        holder.titleTextView.text = note.title

        holder.itemView.setOnClickListener {
            val fragment = NoteDetailFragment.newInstance(note.title, note.description)
            fragment.show((context as FragmentActivity).supportFragmentManager, "note_detail")
        }
    }

    override fun getItemCount(): Int = noteList.size //? в чем отличия от return

    fun updateNotes(notes: List<Note>) {
        noteList.clear()
        noteList.addAll(notes)
        notifyDataSetChanged()
    }

    fun getNoteAtPosition(position: Int): Note {
        return noteList[position]
    }

    inner class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) { // что будет если перед классом добавить inner
        val titleTextView: TextView = itemView.findViewById(R.id.noteTitle)
    }
}