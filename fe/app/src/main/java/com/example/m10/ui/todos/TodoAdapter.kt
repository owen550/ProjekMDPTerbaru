package com.example.m10.ui.todos

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.m10.data.Todo
import com.example.m10.databinding.TodoListItemBinding

class TodoAdapter: ListAdapter<Todo, TodoAdapter.ViewHolder>(
    object : DiffUtil.ItemCallback<Todo>(){
        override fun areItemsTheSame(oldItem: Todo, newItem: Todo): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Todo, newItem: Todo): Boolean {
            return oldItem.id == newItem.id &&
                    oldItem.content == newItem.content &&
                    oldItem.createdAt == newItem.createdAt &&
                    oldItem.completed == newItem.completed
        }
    }
) {

    var onCompleteClickListener:((Todo)->Unit)? = null
    var onEditClickListener:((Todo)->Unit)? = null
    var onDeleteClickListener:((Todo)->Unit)? = null

    class ViewHolder(val binding: TodoListItemBinding):
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = TodoListItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val todo = getItem(position)
        holder.binding.contentTvTodoLi.text = todo.content
        holder.binding.completeIbTodoLi.setImageResource(
            if(todo.completed){
                android.R.drawable.checkbox_on_background
            }
            else{
                android.R.drawable.checkbox_off_background
            }
        )

        holder.binding.dateTvTodoLi.text = todo.createdAt.toString()
        if(todo.completed){
            holder.binding.editIbTodoLi.visibility = View.GONE
        }
        else{
            holder.binding.editIbTodoLi.visibility = View.VISIBLE
        }
        holder.binding.completeIbTodoLi.setOnClickListener {
            onCompleteClickListener?.invoke(todo)
        }
        holder.binding.editIbTodoLi.setOnClickListener {
            onEditClickListener?.invoke(todo)
        }
        holder.binding.deleteIbTodoLi.setOnClickListener {
            onDeleteClickListener?.invoke(todo)
        }
    }
}