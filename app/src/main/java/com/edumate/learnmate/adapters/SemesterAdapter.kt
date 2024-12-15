package com.edumate.learnmate.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.edumate.learnmate.R
import com.edumate.learnmate.activities.SubjectActivity
import com.edumate.learnmate.models.Semester

class SemesterAdapter(private val context: Context, private val semester: List<Semester>) :
    RecyclerView.Adapter<SemesterAdapter.SemesterViewHolder>() {

    inner class SemesterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val semesterViewTitle: TextView = itemView.findViewById(R.id.semesterName)
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
        holder.itemView.setOnClickListener {
            val intent = Intent(context, SubjectActivity::class.java)
            intent.putExtra("TITLE", semester[position].title)
            intent.putExtra("DEPARTMENT", semester[position].department)
            context.startActivity(intent)
        }
    }
}