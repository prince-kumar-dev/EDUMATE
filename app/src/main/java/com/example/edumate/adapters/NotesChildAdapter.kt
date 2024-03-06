package com.example.edumate.adapters

import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.edumate.R
import com.example.edumate.models.NotesChildItem

class NotesChildAdapter(private val notesChildList: List<NotesChildItem>):
    RecyclerView.Adapter<NotesChildAdapter.NotesChildViewHolder>(){

        inner class NotesChildViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val logo: ImageView = itemView.findViewById(R.id.notesChildLogoIv)
            val title: TextView = itemView.findViewById(R.id.notesChildTitleTv)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesChildViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.child_item_notes, parent, false)
        return NotesChildViewHolder(view)
    }

    override fun getItemCount(): Int {
        return notesChildList.size
    }

    override fun onBindViewHolder(holder: NotesChildViewHolder, position: Int) {
        holder.logo.setImageResource(notesChildList[position].logo)
        holder.title.text = notesChildList[position].title
    }
}