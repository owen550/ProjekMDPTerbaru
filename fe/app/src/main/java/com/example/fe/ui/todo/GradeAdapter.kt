package com.example.fe.ui.todo

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.fe.data.GradeUiModel
import com.example.fe.databinding.ItemGradeBinding

class GradeAdapter(
    private val onActionClick: ((GradeUiModel) -> Unit)? = null
) : ListAdapter<GradeUiModel, GradeAdapter.GradeViewHolder>(GradeDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GradeViewHolder {
        val binding = ItemGradeBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return GradeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GradeViewHolder, position: Int) {
        holder.bind(getItem(position), onActionClick)
    }

    class GradeViewHolder(private val binding: ItemGradeBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: GradeUiModel, onActionClick: ((GradeUiModel) -> Unit)?) {
            // 1. Mengambil txtCourseTitle dari objek Course
            binding.txtCourseTitle.text = item.course.title

            // 2. Mengambil txtCoursePoints (Poin kumulatif mahasiswa) dari objek User
            // Menggunakan operator ?: 0 jika points bernilai null
            val totalPoints = item.studentUser.points ?: 0
            binding.txtCoursePoints.text = "★ $totalPoints Points"

            // 3. Mengambil txtScoreValue (Nilai tugas/kuis terkait) dari objek StudentSubmission
            // Menggunakan operator ?: 0 jika score belum dinilai (null)
            val submissionScore = item.submission.score ?: 0
            binding.txtScoreValue.text = submissionScore.toString()

            // Listener klik item (berlaku sama baik di halaman Student maupun Teacher)
            binding.root.setOnClickListener {
                onActionClick?.invoke(item)
            }
        }
    }

    // DiffCallback menggunakan ListAdapter untuk efisiensi komparasi data RecyclerView
    class GradeDiffCallback : DiffUtil.ItemCallback<GradeUiModel>() {
        override fun areItemsTheSame(oldItem: GradeUiModel, newItem: GradeUiModel): Boolean {
            // Membandingkan kesamaan item berdasarkan Primary Key dari tabel student_submissions
            return oldItem.submission.id == newItem.submission.id
        }

        override fun areContentsTheSame(oldItem: GradeUiModel, newItem: GradeUiModel): Boolean {
            // Membandingkan kesamaan konten data secara menyeluruh
            return oldItem == newItem
        }
    }
}