package com.example.bender

import android.app.Activity
import android.app.Person
import android.content.Context
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.nfc.Tag
import android.support.v4.content.ContextCompat.startActivity
import android.support.v7.view.menu.ActionMenuItemView
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.*
import android.widget.*

@Suppress("DEPRECATION")
class NoteViewAdapter (var titlebox: ArrayList<String>, var context: Context, var BD: SQLiteDatabase): RecyclerView.Adapter<NoteViewAdapter.ViewHolder>() {

    override fun getItemCount() = titlebox.size

    private var seletedItem: Int = -1


    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {

        val itemView = LayoutInflater.from(p0.context).inflate(R.layout.title_list_view, p0, false)

        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        p0.textView!!.setOnClickListener({
            var title = titlebox[p1]
            val noteView = Intent(context, NoteViewActivity::class.java)
            noteView.putExtra("Title", title)
            context.startActivity(noteView)

        })
       p0.textView!!.setOnLongClickListener(View.OnLongClickListener {
            seletedItem = p1
           false

       })
        p0?.textView?.text = titlebox[p1]
    }

    fun deleteNote() {
        var query = "SELECT photo FROM Note WHERE title = ?;"
        var cursor = BD.rawQuery(query, arrayOf(titlebox[seletedItem]))
        cursor.moveToFirst()
        val photoid = cursor.getInt(cursor.getColumnIndex("photo"))
        cursor.close()
        BD.delete("Photo", "Note = ?", arrayOf(photoid.toString()))
        BD.delete("Note", "Title = ?", arrayOf(titlebox[seletedItem]))
    }

    fun rewriteNote() {

    }






    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView), View.OnCreateContextMenuListener {
      var textView: TextView? = null
      init {
          textView = itemView?.findViewById(R.id.text_list_item)
          itemView.setOnCreateContextMenuListener(this)
      }

        override fun onCreateContextMenu(p0: ContextMenu?, p1: View?, p2: ContextMenu.ContextMenuInfo?) {

            p0!!.add(Menu.NONE, R.id.re_note, Menu.NONE, "Редактировать")
            p0.add(Menu.NONE, R.id.del_note, Menu.NONE, "Удалить")

        }


    }

}


