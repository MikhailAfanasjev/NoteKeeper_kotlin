package com.example.myapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.annotation.Nullable
import androidx.fragment.app.DialogFragment
import com.example.myapplication.R

class NoteDetailFragment : DialogFragment() {

    companion object {
        private const val ARG_TITLE = "title"
        private const val ARG_DESCRIPTION = "description"

        fun newInstance(title: String, description: String): NoteDetailFragment { // Фабричный метод для создания фрагмента
            val fragment = NoteDetailFragment() // Создаем новый экземпляр фрагмента
            val args = Bundle().apply { // Создаем Bundle для передачи аргументов
                putString(ARG_TITLE, title) // Добавляем заголовок
                putString(ARG_DESCRIPTION, description) // Добавляем описание
            }
            fragment.arguments = args // Устанавливаем аргументы фрагмента
            return fragment // Возвращаем созданный фрагмент
        }
    }

    @Nullable
    override fun onCreateView(@NonNull inflater: LayoutInflater,
                              @Nullable container: ViewGroup?, @Nullable savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_note_detail, container, false)
        val titleTextView: TextView = view.findViewById(R.id.noteTitle)
        val descriptionTextView: TextView = view.findViewById(R.id.noteDescription)

        arguments?.let {
            titleTextView.text = it.getString(ARG_TITLE)
            descriptionTextView.text = it.getString(ARG_DESCRIPTION)
        }

        return view
    }
}