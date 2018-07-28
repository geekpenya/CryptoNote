package com.example.bender

import android.net.Uri
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.bender.R.id.textView

class PhotoViewAdapter(var uribox: ArrayList<Uri>): RecyclerView.Adapter<PhotoViewAdapter.ViewHolder>() {

    override fun getItemCount() = uribox.size

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): PhotoViewAdapter.ViewHolder {
        val itemView = LayoutInflater.from(p0.context).inflate(R.layout.photo_list_view, p0, false)
        return PhotoViewAdapter.ViewHolder(itemView)
    }

    override fun onBindViewHolder(p0: PhotoViewAdapter.ViewHolder, p1: Int) {
        p0?.photoView?.setImageURI( uribox[p1])
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var photoView: ImageView? = null
        init {
            photoView = itemView?.findViewById(R.id.photoView)
        }
    }

}