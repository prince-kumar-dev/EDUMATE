package com.example.edumate.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.edumate.R
import com.example.edumate.models.StudyPlaylist

class StudyPlaylistAdapter(private val context: Context, private val studyPlaylist: ArrayList<StudyPlaylist>):
    ArrayAdapter<StudyPlaylist>(context, R.layout.list_item){

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val inflater: LayoutInflater = LayoutInflater.from(context)
        val view: View = inflater.inflate(R.layout.list_item, null)

        val playlistTitle: TextView = view.findViewById(R.id.playlistTxt)

        playlistTitle.text = studyPlaylist[position].title



        return view
    }
}