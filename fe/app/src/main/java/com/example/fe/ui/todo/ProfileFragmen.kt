package com.example.fe.ui.todo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.fe.R
import com.example.fe.databinding.FragmentDashboardBinding
import com.example.fe.databinding.FragmentProfileBinding
import com.example.fe.user


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
        uiSetup()
        onObserve()
        onListener()
    }

    fun uiSetup(){
        binding.txtName.setText(user!!.username.toString())
        binding.txtEmail.setText(user!!.email.toString())
        binding.txtPoints.setText(user!!.points.toString())
    }

    fun onObserve(){

    }

    fun onListener(){
        // my courses
        binding.btnMyCourses.setOnClickListener {

        }
        // my grades
        binding.btnMyGrades.setOnClickListener {

        }
        // subscribe
        binding.btnSubscription.setOnClickListener {
            findNavController().navigate(R.id.paymentDetailFragment)
        }
        // log out
        binding.btnLogout.setOnClickListener {
            requireActivity().finish()
        }
    }
}