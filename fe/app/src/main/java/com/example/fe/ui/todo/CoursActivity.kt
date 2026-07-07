package com.example.fe.ui.todo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.fe.R
import com.example.fe.databinding.FragmentCoursActivityBinding
import com.example.fe.databinding.FragmentDashboardBinding

class CoursActivity : Fragment() {
    lateinit var binding: FragmentCoursActivityBinding // ini tolong disesiuaikan di masing masing !!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCoursActivityBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // taruh semua kode di sini !!!!!
    }
}