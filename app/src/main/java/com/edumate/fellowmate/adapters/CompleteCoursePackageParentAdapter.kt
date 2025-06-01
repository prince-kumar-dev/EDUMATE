package com.edumate.fellowmate.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.edumate.fellowmate.R
import com.edumate.fellowmate.models.CompleteParentCoursePackage
import me.relex.circleindicator.CircleIndicator2

class CompleteCoursePackageParentAdapter(
    val context: Context,
    private val completeCoursePackageParentList: List<CompleteParentCoursePackage>
) :
    RecyclerView.Adapter<CompleteCoursePackageParentAdapter.CompleteCoursePackageParentViewHolder>() {

    inner class CompleteCoursePackageParentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val stepTitle: TextView = itemView.findViewById(R.id.stepTitle)
        val stepDescription: TextView = itemView.findViewById(R.id.stepDescription)
        val recyclerView: RecyclerView = itemView.findViewById(R.id.completeCourseParentRecyclerView)
        val dotIndicator: CircleIndicator2 = itemView.findViewById(R.id.indicator)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CompleteCoursePackageParentViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.complete_package_parent_item, parent, false)
        return CompleteCoursePackageParentViewHolder(view)
    }

    override fun getItemCount(): Int {
        return completeCoursePackageParentList.size
    }

    override fun onBindViewHolder(holder: CompleteCoursePackageParentViewHolder, position: Int) {
        val date = completeCoursePackageParentList[position]
        holder.stepTitle.text = date.stepTitle
        holder.stepDescription.text = date.stepDescription.replace("\\n", "\n")

        holder.recyclerView.setHasFixedSize(true)
        holder.recyclerView.layoutManager = LinearLayoutManager(
            holder.itemView.context,
            LinearLayoutManager.HORIZONTAL,
            false
        )

        // Attach SnapHelper to RecyclerView
        val snapHelper = PagerSnapHelper()
        val adapter = CompleteCoursePackageChildAdapter(context, date.mList)
        holder.recyclerView.adapter = adapter

        holder.dotIndicator.attachToRecyclerView(holder.recyclerView, snapHelper)
    }
}