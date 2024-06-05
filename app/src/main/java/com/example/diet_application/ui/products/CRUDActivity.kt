package com.example.diet_application.ui.products

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.diet_application.MainActivity
import com.example.diet_application.Product
import com.example.diet_application.R
import java.util.*

class CRUDActivity : AppCompatActivity() {
    // variables for UI components.
    lateinit var noteTitleEdt: EditText
    lateinit var noteEdt: EditText
    lateinit var saveBtn: Button

    // variable for viewmodal and integer for product id
    lateinit var viewModal: ProductsViewModel
    var noteID = -1;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.crud_products)
        supportActionBar?.hide()

        // initializing view modal
        viewModal = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        ).get(ProductsViewModel::class.java)

        // initializing all variables
        noteTitleEdt = findViewById(R.id.idEdtNoteName)
        noteEdt = findViewById(R.id.idEdtNoteDesc)
        saveBtn = findViewById(R.id.idBtn)

        // getting data passed via an intent
        val noteType = intent.getStringExtra("noteType")
        if (noteType.equals("Edit")) {
            //   setting data to edit text.
            val noteTitle = intent.getStringExtra("noteTitle")
            val noteDescription = intent.getIntExtra("noteDescription", -1)
            noteID = intent.getIntExtra("noteId", -1)
            saveBtn.setText("Update Note")
            noteTitleEdt.setText(noteTitle)
            noteEdt.setText(noteDescription.toString())
        } else {
            saveBtn.setText("Save Note")
        }

        // adding click listener to save button
        saveBtn.setOnClickListener {
            // getting title and desc from edit text
            val noteTitle = noteTitleEdt.text.toString()
            val noteDescription = noteEdt.text.toString().toInt()
            // checking the type and then saving or updating the data
            if (noteType.equals("Edit")) {
                if (noteTitle.isNotEmpty() && noteDescription != 0) {
                    val updatedNote = Product(noteID, noteTitle, null, null, noteDescription)
                    viewModal.updateNote(updatedNote)
                    Toast.makeText(this, "Note Updated..", Toast.LENGTH_LONG).show()
                }
            } else {
                if (noteTitle.isNotEmpty() && noteDescription != 0) {
                    // if the string is not empty > calling a add method to add data to room database
                    viewModal.addNote(Product(0, noteTitle, null, null, noteDescription))
                    Toast.makeText(this, "$noteTitle Added", Toast.LENGTH_LONG).show()
                }
            }

            val showContent = Intent(this, MainActivity::class.java)
            startActivity(showContent)
            this.finish()
        }
    }
}