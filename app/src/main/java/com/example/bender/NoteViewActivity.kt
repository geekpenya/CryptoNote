package com.example.bender

import android.content.Context
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore.Images.Media.getBitmap
import android.support.v7.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_note_view.*

class NoteViewActivity : AppCompatActivity() {
    lateinit var BD: SQLiteDatabase
    val BD_NAME = "NoteBD"
    var title = ""
    var bitmapbox = ArrayList<Bitmap>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note_view)
        BD = openOrCreateDatabase(BD_NAME, Context.MODE_PRIVATE, null)
        title = intent.getStringExtra("Title")
        loadNote()
        loadPhoto()

    }

    private fun loadNote() {

        val query = "SELECT text FROM Note WHERE title = ?;"
        val cursor = BD.rawQuery(query, arrayOf(title) )
        cursor.moveToFirst()
        titleView.text = title
        noteView.text = cursor.getString(cursor.getColumnIndex("text"))
        cursor.close()
    }

    private fun loadPhoto() {
        var flag = false
        var bitmapbox = ArrayList<Bitmap>()
        var query = "SELECT photo FROM Note WHERE title = ?;"
        var cursor = BD.rawQuery(query, arrayOf(title))
        cursor.moveToFirst()
        val photoid = cursor.getInt(cursor.getColumnIndex("photo"))
        cursor.close()
        query = "SELECT photo FROM Photo WHERE Note = ?;"
        cursor = BD.rawQuery(query, arrayOf(photoid.toString()))
        if (cursor.moveToFirst()) {
            bitmapbox.add(getBitmap(cursor.getBlob(cursor.getColumnIndex("photo"))))
            flag = true
        }
        while (cursor.moveToNext()) {
            bitmapbox.add(getBitmap(cursor.getBlob(cursor.getColumnIndex("photo"))))
        }

        if (flag) {
            NotePhotoView.layoutManager = LinearLayoutManager(this)
            NotePhotoView.adapter = PhotoViewAdapter(bitmapbox)
        }

    }

    private fun getBitmap(byte: ByteArray):Bitmap {
        return BitmapFactory.decodeByteArray(byte,0,byte.size)
    }


}
