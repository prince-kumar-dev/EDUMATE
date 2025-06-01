package com.edumate.fellowmate.adapters

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.edumate.fellowmate.R
import com.edumate.fellowmate.models.NotesChildItem

class NotesChildAdapter(val context: Context,
    private val notesChildList: List<NotesChildItem>
) :
    RecyclerView.Adapter<NotesChildAdapter.NotesChildViewHolder>() {

    inner class NotesChildViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.notesChildTitleTv)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesChildViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.child_item_notes, parent, false)
        return NotesChildViewHolder(view)
    }

    override fun getItemCount(): Int {
        return notesChildList.size
    }

    override fun onBindViewHolder(holder: NotesChildViewHolder, position: Int) {
        holder.title.text = notesChildList[position].title

        holder.itemView.setOnClickListener {
            val uri = Uri.parse(notesChildList[position].url)
            val intent = Intent(Intent.ACTION_VIEW, uri)
            context.startActivity(intent)
        }
    }
}