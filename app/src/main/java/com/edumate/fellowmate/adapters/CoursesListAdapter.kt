package com.edumate.fellowmate.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.edumate.fellowmate.R
import com.edumate.fellowmate.activities.Course
import com.edumate.fellowmate.models.CoursesList

class CoursesListAdapter(private var context: Context, private var coursesList: List<CoursesList>) :
    RecyclerView.Adapter<CoursesListAdapter.CoursesViewHolder>() {

    inner class CoursesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.coursesTitle)
        val iconImageView: ImageView = itemView.findViewById(R.id.coursesIcon)
        val coursesCardContainer: View = itemView.findViewById(R.id.coursesCardContainer)
    }

    fun setFilteredList(coursesList: List<CoursesList>) {
        this.coursesList = coursesList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoursesViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.courses_item, parent, false)
        return CoursesViewHolder(view)
    }

    override fun getItemCount(): Int {
        return coursesList.size
    }

    override fun onBindViewHolder(holder: CoursesViewHolder, position: Int) {
        val data = coursesList[position]
        holder.titleTextView.text = data.title

        Glide.with(context)
            .load(data.iconUrl)
            .into(holder.iconImageView)

        holder.coursesCardContainer.setOnClickListener {
            val intent = Intent(context, Course::class.java)
            intent.putExtra("TITLE", data.title)
            context.startActivity(intent)
        }
    }
}