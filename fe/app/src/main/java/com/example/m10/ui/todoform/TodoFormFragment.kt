package com.example.m10.ui.todoform

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.m10.R
import com.example.m10.TodoViewModelFactory
import com.example.m10.databinding.FragmentTodoFormBinding

class TodoFormFragment : Fragment() {
    lateinit var binding: FragmentTodoFormBinding
    val viewModel by viewModels<TodoFormViewModel>{ TodoViewModelFactory }
    val args by navArgs<TodoFormFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTodoFormBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.cancelBtnTodoform.setOnClickListener {
            findNavController().navigate(R.id.action_todoFormFragment_to_todosFragment)
        }
        if (args.todoId!="") {
            viewModel.setTodo(args.todoId)
            binding.titleTvTodoform.text="Update Todo"
            binding.putBtnTodoform.text="Update"
            binding.idEtTodoform.setText(args.todoId)
        }
        viewModel.content.observe(viewLifecycleOwner) {
            binding.contentEtTodoform.setText(it)
        }
        viewModel.state.observe(viewLifecycleOwner) {
            if(it=="done") {
                findNavController().navigate(R.id.action_todoFormFragment_to_todosFragment)
            }
        }
        binding.putBtnTodoform.setOnClickListener {
            val id = binding.idEtTodoform.text.toString()
            val content = binding.contentEtTodoform.text.toString()
            viewModel.putTodo(id, content)
        }
    }
}