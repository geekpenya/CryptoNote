package com.example.bender

import android.content.Context
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.support.v7.widget.CardView
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.example.bender.NewNoteActivity.Companion.getidfromBD
import kotlinx.android.synthetic.main.activity_main2.*


class Main2Activity : AppCompatActivity() {
    lateinit var BD: SQLiteDatabase
    val BD_NAME = "NoteBD"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        var login = Intent(this, MainActivity::class.java)
        startActivityForResult(login, 1)
    }

    override fun onResume() {
        super.onResume()
        loadNoteTitle()
    }



    fun butplus(view: View) {
        val newNote = Intent(this, NewNoteActivity::class.java)
        startActivity(newNote)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val login = data!!.getBooleanExtra("login", false)
        if (login) {
            loadNoteTitle()

        }

    }

    private fun getTitleNote(): ArrayList<String> {
        var titlebox: ArrayList<String> = ArrayList()

        val query = "SELECT title FROM Note"
        val cursor = BD.rawQuery(query, null)
        cursor.moveToFirst()
        while (cursor.moveToNext()) {
            titlebox.add(cursor.getString(cursor.getColumnIndex("title")))

        }
        cursor.close()
        return titlebox
    }

    fun openNote(view: View) {
        var textView = findViewById<TextView>(view.id)
        Toast.makeText(this, textView.text.toString(),Toast.LENGTH_SHORT ).show()
    }

    fun loadNoteTitle() {
        BD = openOrCreateDatabase(BD_NAME, Context.MODE_PRIVATE, null)



        NoteView.layoutManager = LinearLayoutManager(this)
        NoteView.adapter = NoteViewAdapter(getTitleNote())
    }
}




