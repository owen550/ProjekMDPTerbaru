package com.example.fe.ui.todo

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.fe.data.CourseTopic
import com.example.fe.data.TopicMaterial
import com.example.fe.databinding.ItemActivityBinding

// iki gawe course topic, namane item_activirty lek gak salah
class CourseTopicDiffCallback : DiffUtil.ItemCallback<CourseTopic>() {
    override fun areItemsTheSame(
        oldItem: CourseTopic,
        newItem: CourseTopic
    ): Boolean {
        // Sesuaikan dengan id dari model topic Anda (misal: oldItem.id == newItem.id)
        return oldItem.hashCode() == newItem.hashCode()
    }
    override fun areContentsTheSame(
        oldItem: CourseTopic,
        newItem: CourseTopic
    ): Boolean {
        return oldItem == newItem
    }
}

// ======================== FUNCTION =========================
class CourseTopicAdapter(
    private val onItemClick: (CourseTopic) -> Unit
) : ListAdapter<CourseTopic, CourseTopicAdapter.CourseTopicViewHolder>(CourseTopicDiffCallback()) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CourseTopicViewHolder {
        val binding = ItemActivityBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CourseTopicViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: CourseTopicViewHolder,
        position: Int
    ) {
        holder.bind(getItem(position))
    }

    inner class CourseTopicViewHolder(private val binding: ItemActivityBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(topic: CourseTopic) {
            // === edit isinya ===
             binding.txtActivityTitle.setText(topic.title)
             binding.txtActivityDesc.setText(topic.description)

            // === fitur binding ===
            binding.outer.setOnClickListener { // kalau di run dapatkan course topic saat ini
                onItemClick(topic)
            }
        }
    }
}