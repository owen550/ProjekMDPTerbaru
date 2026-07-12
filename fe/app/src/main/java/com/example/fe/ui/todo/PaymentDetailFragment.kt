package com.example.fe.ui.todo

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.fe.R
import com.example.fe.TodoViewModelFactory
import com.example.fe.databinding.FragmentPaymentDetailBinding
import com.example.fe.ui.todoform.TodoFormViewModel
import com.example.fe.user
import kotlin.getValue

class PaymentDetailFragment : Fragment() {

    private var _binding: FragmentPaymentDetailBinding? = null
    private val binding get() = _binding!!

    private val viewModel: TodosViewModel by viewModels {
        TodoViewModelFactory
    }

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
        // main code
        onSetup()
        onListener()
        onObserve()
    }

    fun onSetup(){
        if(user!!.tier == "free"){ // free
            binding.tvMemberStatus.setText("You're a Free Member")
            binding.tvSubtitle.setText("Unlock all premium features and enjoy a better learning experience.")
            binding.btnSubscribe.visibility = View.VISIBLE
        }
        else if(user!!.tier == "premium"){
            binding.tvMemberStatus.setText("You're a Premium Member")
            binding.tvSubtitle.setText("Now You Unlock all premium features and enjoy a better learning experience.")
            binding.btnSubscribe.visibility = View.GONE
        }
        else{ // admin
            binding.tvMemberStatus.setText("You're a Admin")
            binding.tvSubtitle.setText("The premium features we had now is")
            binding.btnSubscribe.visibility = View.GONE
        }
    }

    fun onListener(){
        binding.btnBack.setOnClickListener {
            findNavController().navigate(R.id.profileFragmen)
        }

        binding.btnSubscribe.setOnClickListener {
            var amount = 100000 // biaya subscribe
            viewModel.buatTransaksi(amount)
        }
    }

    fun onObserve(){
        viewModel.redirectToPayment.observe(viewLifecycleOwner) { url ->
            if (!url.isNullOrEmpty()) {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                startActivity(intent)
                // === lek udah suru logout ===
                requireActivity().finish()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}