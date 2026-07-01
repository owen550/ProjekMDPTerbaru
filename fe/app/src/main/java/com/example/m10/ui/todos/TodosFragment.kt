package com.example.m10.ui.todos

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.m10.R
import com.example.m10.TodoViewModelFactory
import com.example.m10.databinding.FragmentTodosBinding

class TodosFragment : Fragment() {
    val viewModel by viewModels<TodosViewModel>{ TodoViewModelFactory }
    lateinit var binding: FragmentTodosBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTodosBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.addBtnTodos.setOnClickListener {
            val action = TodosFragmentDirections.actionTodosFragmentToTodoFormFragment(
                "")
            findNavController().navigate(action)
        }
        binding.syncBtnTodos.setOnClickListener {
            viewModel.sync()
        }

        val adapter = TodoAdapter()
        adapter.onCompleteClickListener = {
            viewModel.toggleCompleted(it.id, !it.completed)
        }
        adapter.onEditClickListener = {
            val action = TodosFragmentDirections.actionTodosFragmentToTodoFormFragment(it.id)
            findNavController().navigate(action)
        }
        adapter.onDeleteClickListener = {
            viewModel.deleteTodo(it.id)
        }
        viewModel.todos.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
        binding.todosRvTodos.adapter = adapter
        binding.todosRvTodos.layoutManager = LinearLayoutManager(requireContext(),
            LinearLayoutManager.VERTICAL,false)
        viewModel.init()
        binding.filterSpinnerTodos.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                viewModel.setFilter(resources.getStringArray(R.array.todo_filter_options)[position])
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }
    }
}