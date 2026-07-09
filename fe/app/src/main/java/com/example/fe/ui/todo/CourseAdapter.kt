package com.example.fe.ui.todo

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.fe.data.Course
import com.example.fe.databinding.ItemCourseBinding

class CourseDiffCallback : DiffUtil.ItemCallback<Course>() {
    override fun areItemsTheSame(
        oldItem: Course,
        newItem: Course
    ): Boolean {
        return oldItem.id == newItem.id
    }
    override fun areContentsTheSame(
        oldItem: Course,
        newItem: Course
    ): Boolean {
        return oldItem == newItem
    }
}

class CourseAdapter(private val listener: OnCourseClickListener) : ListAdapter<Course, CourseAdapter.CourseViewHolder>(CourseDiffCallback()) {

    interface OnCourseClickListener {
        fun onBookmark(course: Course)
        fun onOpenDetail(course: Course)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CourseViewHolder {
        val binding = ItemCourseBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CourseViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: CourseViewHolder,
        position: Int
    ) {
        holder.bind(getItem(position))
    }

    inner class CourseViewHolder(private val binding: ItemCourseBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(course: Course) {
            binding.txtTitle.text = course.title
            binding.txtDesc.text = course.category

            binding.btnAdd.setOnClickListener {
                listener.onBookmark(course)
            }

            binding.btnDetail.setOnClickListener {
                listener.onOpenDetail(course)
            }
        }
    }
}