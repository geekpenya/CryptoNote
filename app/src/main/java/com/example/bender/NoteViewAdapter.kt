package com.example.bender

import android.app.Person
import android.nfc.Tag
import android.support.v7.view.menu.ActionMenuItemView
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import kotlin.coroutines.experimental.coroutineContext

class NoteViewAdapter (var titlebox: ArrayList<String>): RecyclerView.Adapter<NoteViewAdapter.ViewHolder>() {

    override fun getItemCount() = titlebox.size

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {

        val itemView = LayoutInflater.from(p0.context).inflate(R.layout.title_list_view, p0, false)

        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        p0?.textView?.text = titlebox[p1]


    }


  class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)  {
      var textView: TextView? = null
      init {
          itemView.setOnClickListener {v: View ->
              var pos = adapterPosition
              print(pos)

          }
          textView = itemView?.findViewById(R.id.text_list_item)
      }














  }

}


