package com.example.bender

import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager


import kotlinx.android.synthetic.main.activity_view_add_photo.*

class ViewAddPhoto : AppCompatActivity() {
    lateinit var uribox: ArrayList<Uri>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_add_photo)
        uribox = intent.getParcelableArrayListExtra<Uri>("photoList")

        PhotoView.layoutManager = LinearLayoutManager(this)
        PhotoView.adapter = PhotoViewAdapter(uribox)
    }


}
