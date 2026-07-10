package com.example.fe.ui.todo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.fe.data.Course
import com.example.fe.databinding.ItemCourseBinding

class CourseDiffCallback : DiffUtil.ItemCallback<Course>() {
    override fun areItemsTheSame(oldItem: Course, newItem: Course): Boolean = oldItem.id == newItem.id
    override fun areContentsTheSame(oldItem: Course, newItem: Course): Boolean = oldItem == newItem
}

class CourseAdapter(
    private val listener: OnCourseClickListener
) : ListAdapter<Course, CourseAdapter.CourseViewHolder>(CourseDiffCallback()) {

    private var enrolledCourseIds: Set<Int> = emptySet()
    private var isTeacher: Boolean = false

    interface OnCourseClickListener {
        fun onBookmark(course: Course)
        fun onOpenDetail(course: Course)
    }

    fun updateEnrollmentData(enrolledIds: Set<Int>, teacherMode: Boolean = false) {
        this.enrolledCourseIds = enrolledIds
        this.isTeacher = teacherMode
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourseViewHolder {
        val binding = ItemCourseBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CourseViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CourseViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class CourseViewHolder(private val binding: ItemCourseBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(course: Course) {
            binding.txtTitle.text = course.title
            binding.txtDesc.text = course.category

            // Logic for the "+" button:
            // 1. Hide if Teacher (Teacher manages their own courses elsewhere)
            // 2. Hide if Student is already enrolled
            if (isTeacher || enrolledCourseIds.contains(course.id)) {
                binding.btnAdd.visibility = View.GONE
            } else {
                binding.btnAdd.visibility = View.VISIBLE
            }

            binding.btnAdd.setOnClickListener {
                listener.onBookmark(course)
            }

            binding.btnDetail.setOnClickListener {
                listener.onOpenDetail(course)
            }
        }
    }
}