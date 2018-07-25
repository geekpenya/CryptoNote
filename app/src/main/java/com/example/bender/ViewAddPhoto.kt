package com.example.bender

import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TableLayout

import kotlinx.android.synthetic.main.activity_view_add_photo.*
import kotlinx.android.synthetic.main.activity_view_add_photo.view.*

class ViewAddPhoto : AppCompatActivity() {
    lateinit var uribox: ArrayList<Uri>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_add_photo)
        uribox = intent.getParcelableArrayListExtra<Uri>("photoList")

        for (i in uribox) {
            val imageView = ImageView(this)
            imageView.setImageURI(i)
            val params = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT)
            imageView.layoutParams = params

            LinerView.addView(imageView)

        }
    }
}
