package com.example.edumate.adapters

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.edumate.R
import com.example.edumate.models.Semester
import com.example.edumate.utils.ColorPicker

class SemesterAdapter(val context: Context, val semester: List<Semester>):
    RecyclerView.Adapter<SemesterAdapter.SemesterViewHolder>(){

    inner class SemesterViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val semesterViewTitle: TextView = itemView.findViewById(R.id.semesterName)
        val cardContainer: CardView = itemView.findViewById(R.id.semesterCardContainer)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SemesterViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.semester_item, parent, false)
        return SemesterViewHolder(view)
    }

    override fun getItemCount(): Int {
        return semester.size
    }

    override fun onBindViewHolder(holder: SemesterViewHolder, position: Int) {
        holder.semesterViewTitle.text = semester[position].title
        // holder.cardContainer.setCardBackgroundColor(Color.parseColor(ColorPicker.getColor()))
    }
}