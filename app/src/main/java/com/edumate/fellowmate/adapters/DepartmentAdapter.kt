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
import com.edumate.fellowmate.activities.SemesterActivity
import com.edumate.fellowmate.models.Department

class DepartmentAdapter(private var context: Context, private val departments: List<Department>) :
    RecyclerView.Adapter<DepartmentAdapter.DepartmentViewHolder>() {

    inner class DepartmentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var departmentViewTitle: TextView = itemView.findViewById(R.id.departmentTitle)
        var iconView: ImageView = itemView.findViewById(R.id.departmentIcon)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DepartmentViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.department_item, parent, false)
        return DepartmentViewHolder(view)
    }

    override fun getItemCount(): Int {
        return departments.size
    }

    override fun onBindViewHolder(holder: DepartmentViewHolder, position: Int) {

        holder.departmentViewTitle.text = departments[position].title

        Glide.with(context)
            .load(departments[position].iconUrl)
            .into(holder.iconView)

        holder.itemView.setOnClickListener {
            val intent = Intent(context, SemesterActivity::class.java)
            intent.putExtra("TITLE", departments[position].title)
            context.startActivity(intent)
        }

    }
}
