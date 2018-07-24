package com.example.bender

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class Main2Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        var login = Intent(this, MainActivity::class.java)
        startActivity(login)
    }

    fun butplus(view: View) {
        val newNote = Intent(this, NewNoteActivity::class.java)
        startActivity(newNote)

    }
}
