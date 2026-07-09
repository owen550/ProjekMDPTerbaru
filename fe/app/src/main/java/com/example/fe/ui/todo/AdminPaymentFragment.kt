package com.example.fe.ui.todo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fe.R
import com.example.fe.TodoViewModelFactory
import com.example.fe.databinding.FragmentAdminPaymentBinding

class AdminPaymentFragment : Fragment() {

    private var _binding: FragmentAdminPaymentBinding? = null
    private val binding get() = _binding!!

    private val viewModel: TodosViewModel by viewModels { TodoViewModelFactory }

    private lateinit var paymentAdapter: AdminPaymentAdapter


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAdminPaymentBinding.inflate(
            inflater,
            container,
            false
        )

        return binding.root
    }


    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        observeViewModel()

        viewModel.fetchAllPayments()

        binding.btnStopServer.setOnClickListener {
            Toast.makeText(
                requireContext(),
                "Stopping server...",
                Toast.LENGTH_SHORT
            ).show()
        }
    }


    private fun setupRecyclerView() {

        paymentAdapter = AdminPaymentAdapter { payment ->

            val bundle = Bundle().apply {
                putInt("userId", payment.user_id)
                putInt("paymentId", payment.id)
            }

            findNavController().navigate(
                R.id.action_adminPaymentFragment_to_paymentDetailFragment,
                bundle
            )
        }


        binding.rvAdminPayment.apply {
            adapter = paymentAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }


    private fun observeViewModel() {

        viewModel.payments.observe(viewLifecycleOwner) { payments ->

            paymentAdapter.submitList(payments)

        }


        viewModel.message.observe(viewLifecycleOwner) { message ->

            if (!message.isNullOrEmpty()) {

                Toast.makeText(
                    requireContext(),
                    message,
                    Toast.LENGTH_SHORT
                ).show()

            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}