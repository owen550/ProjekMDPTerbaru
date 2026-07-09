package com.example.fe.ui.todo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.fe.TodoViewModelFactory
import com.example.fe.databinding.FragmentPaymentDetailBinding

class PaymentDetailFragment : Fragment() {

    private var _binding: FragmentPaymentDetailBinding? = null
    private val binding get() = _binding!!

    private val viewModel: TodosViewModel by viewModels { TodoViewModelFactory }

    private var userId: Int = 0
    private var paymentId: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPaymentDetailBinding.inflate(
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

        userId = arguments?.getInt("userId") ?: 0
        paymentId = arguments?.getInt("paymentId") ?: 0

        observeViewModel()

        // Avoid redundant fetching if data is already present in ViewModel
        if (viewModel.paymentDetail.value?.id != paymentId) {
            viewModel.fetchPaymentDetail(
                userId,
                paymentId
            )
        }

        binding.btnStopServer.setOnClickListener {
            Toast.makeText(
                requireContext(),
                "Stopping server...",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun observeViewModel() {
        viewModel.paymentDetail.observe(
            viewLifecycleOwner
        ) { payment ->
            payment?.let {
                updateUI(it)
            }
        }

        viewModel.message.observe(
            viewLifecycleOwner
        ) { message ->
            if (!message.isNullOrEmpty()) {
                Toast.makeText(
                    requireContext(),
                    message,
                    Toast.LENGTH_SHORT
                ).show()
                // Reset message to avoid redundant toasts on configuration changes
                viewModel.reset()
            }
        }

        viewModel.loading.observe(viewLifecycleOwner) { isLoading ->
            // You can implement a progress bar here if one exists in the layout
            // binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }
    }

    private fun updateUI(payment: com.example.fe.data.Payment) {
        binding.apply {
            tvNameLarge.text = "Payment Detail"

            tvPaymentIdValue.text = payment.id.toString()
            tvUserIdValue.text = payment.user_id.toString()
            tvOrderIdValue.text = payment.order_id
            tvPaymentTokenValue.text = payment.payment_token
            tvAmountValue.text = payment.amount.toString()
            tvPaymentMethodValue.text = payment.payment_method

            tvVaNumberValue.text = payment.va_number ?: "-"
            tvQrUrlValue.text = payment.qr_url ?: "-"
            tvStatusValue.text = payment.status

            tvCreatedAtValue.text = payment.created_at
            tvUpdatedAtValue.text = payment.updated_at
            tvDeletedAtValue.text = payment.deleted_at ?: "-"
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
