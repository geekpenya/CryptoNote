package com.example.bender

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.graphics.Bitmap
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import kotlinx.android.synthetic.main.activity_new_note.*
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.net.URI

class NewNoteActivity : AppCompatActivity() {
    lateinit var BD: SQLiteDatabase
    val BD_NAME = "NoteBD"
    val GALLERY_REQUST = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_note)
        BD = openOrCreateDatabase(BD_NAME, Context.MODE_PRIVATE, null)
    }

    fun addPhoto (view: View) {
        val photoPickIntent = Intent(Intent.ACTION_PICK)
        photoPickIntent.setType("image/*")
        startActivityForResult(photoPickIntent, GALLERY_REQUST )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, imageReturn: Intent?) {
        super.onActivityResult(requestCode, resultCode, imageReturn)
            try {
                val selectImg = imageReturn?.data as Uri
                previewImg.setImageURI(selectImg)
            }
            catch (e: IOException) {
                e.printStackTrace()
            }
        }

    fun butSave (view: View) {

    }

    private fun saveImg() {
        var byte = getBitmapByte(previewImg.drawable as Bitmap)

    }

    private fun getBitmapByte(bitmap: Bitmap):ByteArray {
        val outputstream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 0, outputstream )
        return outputstream.toByteArray()
    }





}
