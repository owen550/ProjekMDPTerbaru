package com.example.fe.ui.todo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.fe.R
import com.example.fe.databinding.FragmentDashboardBinding
import com.example.fe.databinding.FragmentProfileBinding


class ProfileFragmen : Fragment() {

    lateinit var binding: FragmentProfileBinding // ini tolong disesiuaikan di masing masing !!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // === main kode di sini
    }
}